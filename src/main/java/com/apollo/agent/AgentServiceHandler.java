package com.apollo.agent;

import com.apollo.discovery.MasterDiscovery;
import com.apollo.docker.Docker;
import com.apollo.executor.TaskExecutor;
import com.apollo.thriftgen.*;
import com.apollo.timing.Clock;
import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AgentServiceHandler extends Thread implements AgentService.Iface {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentServiceHandler.class);

    // If we haven't been pinged for 3 minutes, we're considered disconnected
    // and we try to reconnect to master.
    private static final long DESIRED_PING_MILLIS = 1000l * 60 * 3;
    // try and reconnect every minute.
    private static final long RECONNECT_MILLIS = 1000l * 60 * 1;
    // verify every 2 minutes
    private static final long VERIFY_MILLIS = 1000l * 60 * 2;

    private final Docker docker;
    private final MachineDescriptor machineDescriptor;
    private final Map<TaskID, TaskExecutor> executorMap = new HashMap<>();
    private final StatusUpdateManager updateManager;
    private final Object PING_LOCK = new Object();
    private boolean hadPing = false;
    private Clock pingClock;
    private Clock reconnectClock;
    private Clock verifyClock;

    public AgentServiceHandler(String ip, int port, Docker docker, StatusUpdateManager updateManager) {
        super("AgentServiceHandler background");
        Preconditions.checkNotNull(ip, "ip must not be null!");
        this.machineDescriptor = new MachineDescriptor("", ip, port);
        this.docker = Preconditions.checkNotNull(docker, "docker must not be null!");
        this.updateManager = Preconditions.checkNotNull(updateManager, "updateManager must not be null!");
        this.pingClock = new Clock(TimeUnit.MILLISECONDS, DESIRED_PING_MILLIS);
        this.verifyClock = new Clock(TimeUnit.MILLISECONDS, VERIFY_MILLIS);
    }

    @Override
    public void run() {
        LOGGER.info("AgentService thread started.");
        MasterDiscovery.getSingleton().registerCallback(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Master got updated!");
                // force reconnect.
                synchronized (PING_LOCK) {
                    hadPing = false;
                    reconnectClock = null;
                }
            }
        });
        while (true) {
            // Step 1: Check if we're disconnected from the scheduler.
            boolean disconnected = false;
            synchronized (PING_LOCK) {
                disconnected = !hadPing || pingClock.isUp();
            }
            if (reconnectClock != null) {
                disconnected &= reconnectClock.isUp();
            }

            // TODO: if we're disconnected for a long period of time we should pause/stop our tasks.
            if (disconnected) {
                // Connect to master!
                LOGGER.info("Agent disconnected from current master, re-registering!");
                if (connect()) {
                    reconnectClock = null;
                } else {
                    reconnectClock = new Clock(TimeUnit.MILLISECONDS, RECONNECT_MILLIS);
                }
            }

            // Step 2: Verify our tasks with the current master and remove any we're not supposed
            // to be running.
            // TODO: ^
            if (!disconnected && verifyClock.isUp()) {
                LOGGER.info("Verifying tasks!");
                verifyClock.restart();
                List<TaskID> managedTasks = new ArrayList<TaskID>();
                synchronized (executorMap) {
                    Set<TaskID> tasks = executorMap.keySet();
                    for (TaskID t : tasks) managedTasks.add(t);
                }
                Response res = verifyTasks(managedTasks);
                if (res != null) {
                    SchedulerVerifyTasksResult result = res.getResult().getSchedulerVerifyTasksResult();
                    List<TaskID> verified = result.getVerifiedTasks();
                    for (TaskID t : managedTasks) {
                        if (!verified.contains(t)) {
                            LOGGER.info("Had to remove an unverified task [taskID=" + t + "].");
                            // must call t!
                            killTask(t);
                        }
                    }
                }
            }

            // Step 3: Sleep and give other threads a chance to run.
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                LOGGER.error("AgentService thread interrupted!");
                continue;
            }
        }
    }

    private Response verifyTasks(List<TaskID> tasks) {
        Preconditions.checkNotNull(tasks, "tasks must not be null!");
        try {
            MasterDiscovery disc = MasterDiscovery.getSingleton();
            TSocket socket = new TSocket(disc.getMasterIP(), disc.getMasterPort());
            TTransport transport = new TFramedTransport(socket);
            SchedulerAgentService.Client client = new SchedulerAgentService.Client(
                    new TBinaryProtocol(transport)
            );
            transport.open();
            Response resp = client.verifyTasks(updateManager.getAgentID(), tasks);
            transport.close();
            return resp;
        } catch (Exception ex) {
            LOGGER.info("Error verifying tasks with the current master!");
            return null;
        }
    }

    private boolean connect() {
        LOGGER.info("Trying to connect to the current master.");
        try {
            MasterDiscovery disc = MasterDiscovery.getSingleton();
            TSocket socket = new TSocket(disc.getMasterIP(), disc.getMasterPort());
            TTransport transport = new TFramedTransport(socket);
            SchedulerAgentService.Client client = new SchedulerAgentService.Client(
                    new TBinaryProtocol(transport)
            );
            transport.open();
            Response resp = client.registerAgent(machineDescriptor);
            AgentID agent = resp.getResult().getAgentRegisterResult().getId();

            updateManager.setAgentID(agent);
            transport.close();
            synchronized (PING_LOCK) {
                hadPing = true;
                pingClock.restart();
            }
            LOGGER.info("Connected to the current master successfully! [agentID=" + agent + "]");
            return true;
        } catch (Exception ex) {
            LOGGER.info("Error connecting to the current master!");
            return false;
        }
    }

    // verifies that the provided schedulerid is the master.
    // if masterdiscovery doesnt give us an id then we ignore this call.
    private Response verifyMaster(SchedulerID scheduler) {
        Preconditions.checkNotNull(scheduler, "scheduler must not be null!");
        SchedulerID master = MasterDiscovery.getSingleton().getMasterID();
        if (master != null && !scheduler.equals(master)) {
            LOGGER.error("A non-master scheduler tried to send this agent a message [schedulerID=" + scheduler + "].");
            return new Response(ResponseCode.WARNING);
        }
        return null;
    }

    @Override
    public Response ping(SchedulerID scheduler) throws TException {
        LOGGER.info("Received a PING() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        synchronized (PING_LOCK) {
            hadPing = true;
            pingClock.restart();
        }
        return new Response(ResponseCode.OK);
    }

    @Override
    public Response getAgentHealth(SchedulerID scheduler) throws TException {
        LOGGER.info("Received a GETAGENTHEALTH() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        // TODO: Return a list of data usage, CPU thrashing, etc...
        return null;
    }

    @Override
    public Response getTaskHealth(SchedulerID scheduler) throws TException {
        LOGGER.info("Received a GETTASKHEALTH() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        // TODO: Return the Status json of each task
        return null;
    }

    @Override
    public Response registerTask(SchedulerID scheduler, TaskDescriptor task) throws TException {
        LOGGER.info("Received a REGISTERTASK() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        synchronized (executorMap) {
            // Registers and executes a task.
            TaskExecutor executor = new TaskExecutor(task, docker, updateManager);
            executorMap.put(new TaskID(task.getId()), executor);
            executor.start();
        }
        return new Response(ResponseCode.OK);
    }

    private Response killTask(TaskID task) {
        Preconditions.checkNotNull(task, "task must not be null!");
        LOGGER.info("Killing task [taskID=" + task + "].");
        synchronized (executorMap) {
            if (!executorMap.containsKey(task)) {
                LOGGER.error("Tried to kill a task we don't run!");
                // Don't treat it as an 'error' since the agent didn't error out.
                // We treat it as an 'invalid request' since that makes more sense.
                return new Response(ResponseCode.INVALID_REQUEST);
            }
            TaskExecutor executor = executorMap.get(task);
            executor.killTask();
            // TODO: remove it from executorMap??
            executorMap.remove(task);
        }
        return null;
    }

    @Override
    public Response killTask(SchedulerID scheduler, TaskID task) throws TException {
        LOGGER.info("Received a KILLTASK() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        Response resp = killTask(task);
        if (resp != null) return resp;
        return new Response(ResponseCode.OK);
    }

    @Override
    public Response purge(SchedulerID scheduler) throws TException {
        LOGGER.info("Received a PURGE() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        // TODO: Purge this agent.
        return null;
    }

    @Override
    public Response kill(SchedulerID scheduler) throws TException {
        LOGGER.info("Received a KILL() rpc.");
        Response verify = verifyMaster(scheduler);
        if (verify != null) {
            return verify;
        }
        // TODO: Kill this agent.
        return null;
    }
}
