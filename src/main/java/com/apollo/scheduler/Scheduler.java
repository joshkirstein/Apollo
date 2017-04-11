package com.apollo.scheduler;

import com.apollo.thriftgen.*;
import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Scheduler extends Thread implements SchedulerAgentService.Iface, SchedulerAdminService.Iface {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    // Ping every 2 minutes.
    public static final long PING_INTERVAL = 1000l * 60l * 2l;

    private final SchedulerStateManager stateManager;
    private final SchedulerID id;
    private volatile boolean isLeader = false;

    public Scheduler(SchedulerStateManager stateManager) {
        super("Scheduler");
        this.stateManager = Preconditions.checkNotNull(stateManager, "stateManager must not be null!");
        this.id = new SchedulerID(UUID.randomUUID().toString());
    }

    public String getSchedulerID() {
        return id.id;
    }

    public void takeLeadership() {
        // TODO: stateManager.replay();
        isLeader = true;
    }

    @Override
    public void run() {
        LOGGER.info("Scheduler thread started.");
        while (true) {
            if (isLeader) {
                // Step 1: try and re-ping agents...
                // TODO: Make asynchronous.
                List<Agent> agents = stateManager.getAgents();
                for (Agent agent : agents) {
                    long curTime = System.currentTimeMillis();
                    if ((curTime - agent.lastPingAttempt) < PING_INTERVAL) continue;
                    agent.lastPingAttempt = curTime;
                    if (!ping(agent)) {
                        LOGGER.info("Agent lost. [agentID=" + agent.getInfo().id + "]");
                        // TODO: Handle lost agent. Also we probably want to do exponential backoff here for time until next
                        // ping. Re-registering resets this. Also when we lose an agent we want to reschedule its tasks.
                        // So we needa do that.
                        LOGGER.info("Rescheduling tasks for agent. [agentID=" + agent.getInfo().id + "].");
                        stateManager.updateLostTasks(agent);
                    } else {
                        agent.lastPingSuccess = System.currentTimeMillis();
                    }
                }

                // Step 2: go through tasks that current exist and look at the ones that aren't scheduled and schedule them!
                List<SchedulerStateManager.TaskAssignment> assignments = stateManager.assignTasks();
                for (SchedulerStateManager.TaskAssignment assign : assignments) {
                    register(assign.agent, assign.task);
                }
            } else {
                //LOGGER.debug("Currently not the leader!");
            }

            // Step 3: Sleep and give up thread quantum.
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                LOGGER.error("Scheduler thread interrupted!");
                continue;
            }
        }
    }

    private void register(MachineDescriptor agent, TaskDescriptor task) {
        Preconditions.checkNotNull(agent, "agent must not be null!");
        Preconditions.checkNotNull(task, "task must not be null!");
        LOGGER.info("Assigning task [taskID=" + task.id + "] to agent [agentID=" + agent.id + "].");
        try {
            TSocket socket = new TSocket(agent.ip, agent.port);
            TTransport transport = new TFramedTransport(socket);
            AgentService.Client client = new AgentService.Client(
                    new TBinaryProtocol(transport)
            );
            transport.open();
            client.registerTask(id, task);
            transport.close();
        } catch (Exception ex) {
            LOGGER.error("Error assigning task to agent! Unassigning.");
            stateManager.assignAgentToTask(new TaskID(task.id), null);
        }
    }

    private boolean ping(Agent agent) {
        Preconditions.checkNotNull(agent, "agent must not be null!");
        LOGGER.info("Trying to ping agent [agentID=" + agent.getInfo().id + "].");
        try {
            TSocket socket = new TSocket(agent.getInfo().ip, agent.getInfo().port);
            TTransport transport = new TFramedTransport(socket);
            AgentService.Client client = new AgentService.Client(
                    new TBinaryProtocol(transport)
            );
            transport.open();
            client.ping(id);
            transport.close();
            LOGGER.info("Successfully pinged agent! [agentID=" + agent.getInfo().id + "]");
            return true;
        } catch (Exception ex) {
            LOGGER.info("Error pinging agent!");
            return false;
        }
    }

    private Response verifyLeader() {
        if (!isLeader) {
            LOGGER.error("An RPC was made to this non-leader scheduler.");
            return new Response(ResponseCode.WARNING);
        }
        return null;
    }

    @Override
    public Response verifyTasks(AgentID id, List<TaskID> tasks) throws TException {
        LOGGER.info("Received VERIFYTASKS() RPC!");
        Response verify = verifyLeader();
        if (verify != null) return verify;
        SchedulerVerifyTasksResult res = stateManager.verifyTasks(id, tasks);
        return new Response(ResponseCode.OK).setResult(Result.schedulerVerifyTasksResult(res));
    }

    @Override
    public Response registerAgent(MachineDescriptor thiz) throws AgentRegistrationException {
        LOGGER.info("Received REGISTERAGENT() RPC!");
        Response verify = verifyLeader();
        if (verify != null) return verify;
        Agent agent = stateManager.addAgent(thiz);
        // Count this re-registering event as a ping.
        agent.lastPingSuccess = System.currentTimeMillis();
        LOGGER.info("Successfully registered an agent with id [agentID=" + agent + "].");
        return new Response(ResponseCode.OK).setResult(Result.agentRegisterResult(new AgentRegisterResult(new AgentID(agent.getInfo().id))));
    }

    @Override
    public Response updateTaskStatus(AgentID id, TaskID task, StatusUpdate update) throws SchedulerTaskStatusUpdateException {
        LOGGER.info("Received UPDATETASKSTATUS() RPC!");
        Response verify = verifyLeader();
        if (verify != null) return verify;
        boolean allowed = stateManager.handleTaskUpdate(id, task, update);
        if (!allowed) {
            return new Response(ResponseCode.WARNING);
        } else {
            return new Response(ResponseCode.OK);
        }
    }
    @Override
    public Response registerTask(TaskDescriptor taskDesc) throws SchedulerTaskRegistrationException {
        LOGGER.info("Received REGISTERTASK() RPC!");
        Response verify = verifyLeader();
        if (verify != null) return verify;
        String idStr = UUID.randomUUID().toString();
        TaskID id = new TaskID(idStr);
        taskDesc.setId(idStr);
        List<StatusUpdate> initStatusList = new ArrayList<StatusUpdate>();
        initStatusList.add(new StatusUpdate(System.currentTimeMillis(), TaskStatus.STAGING));
        Task task = new Task(taskDesc, null, 0, TaskStatus.STAGING, initStatusList);
        stateManager.registerTask(task);
        return new Response(ResponseCode.OK).setResult(Result.schedulerRegisterResult(new SchedulerRegisterResult(id)));
    }

    @Override
    public Response killTask(TaskID task) throws SchedulerTaskKillException, TException {
        LOGGER.info("Received KILLTASK() RPC!");
        Response verify = verifyLeader();
        if (verify != null) return verify;
        // TODO...
        return null;
    }

    @Override
    public Response getAllAgentInfo() throws SchedulerAgentInfoRequestException {
        return null;
    }

    @Override
    public Response getTaskHealth(TaskID task) throws SchedulerTaskStatusRequestException {
        return null;
    }

    @Override
    public Response getAllTaskHealth() throws SchedulerTaskStatusRequestException {
        return null;
    }

    @Override
    public Response takeSnapshot() throws SchedulerSnapshotException {
        return null;
    }

    @Override
    public Response kill() throws TException {
        return null;
    }
}
