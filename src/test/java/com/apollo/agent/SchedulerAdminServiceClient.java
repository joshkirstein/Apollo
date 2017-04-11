package com.apollo.agent;

import com.apollo.thriftgen.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.UUID;

public class SchedulerAdminServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerAdminServiceClient.class);

    /*public static class SchedulerAgentHandler implements SchedulerAgentService.Iface {
        private static final Logger logger = LoggerFactory.getLogger(SchedulerAgentHandler.class);

        @Override
        public Response registerAgent(MachineDescriptor thiz) throws AgentRegistrationException {
            logger.info("RECEIVED REGISTRATION");
            return new Response(ResponseCode.OK);
        }

        @Override
        public Response updateTaskStatus(AgentID id, TaskID task, StatusUpdate update) throws SchedulerTaskStatusUpdateException, TException {
            logger.info("Received a status update for task [taskID=" + task + "] to new state [status=" + update.getStatus() + "].");
            if (update.getMessage() != null) logger.info("MESSAGE: " + update.getMessage());
            return new Response(ResponseCode.OK);
        }
    }

    public static void createScheduler() {
        logger.info("Creating scheduler agent service.");
        SchedulerAgentHandler schedulerAgentHandler = new SchedulerAgentHandler();

        // Spin up agent service handler server.
        SchedulerAgentService.Processor processor = new SchedulerAgentService.Processor(schedulerAgentHandler);
        new Thread(() -> {
            try {
                logger.info("Starting scheduler agent service server on port 6666.");
                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(6666);
                TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
                server.serve();
            } catch (Exception ex) {
                logger.error("scheduler agent service server died unexpectedly. Exiting.");
                logger.error("REASON: " + ex.toString());
                System.exit(1);
            }
        }).start();

        // Sleep a little bit after making the scheduler service.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            logger.info("Main thread interrupted!");
            return;
        }
    }*/

    public static void main(String[] args) throws TException {
        logger.info("SchedulerAdminServiceClient started.");
        //createScheduler();
        spawnTask();
        /*try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
           logger.info("Main thread interrupted!");
           return;
        }*/
    }

    public static void spawnTask() throws TException {
        logger.info("Spawning a task.");
        TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 4355));
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        SchedulerAdminService.Client client = new SchedulerAdminService.Client(protocol);

        String loc = "file:///Users/joshuakirstein/Desktop/image.tz";
        TaskDescriptor desc = new TaskDescriptor("Josh Task", UUID.randomUUID().toString(), new FetcherURI(loc), new HashSet<>());
        System.out.println("TASK: " + desc);

        client.registerTask(desc);

        transport.close();
    }
}
