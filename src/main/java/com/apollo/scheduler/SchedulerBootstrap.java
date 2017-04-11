package com.apollo.scheduler;

import ch.qos.logback.classic.Level;
import com.apollo.agent.AgentBootstrap;
import com.apollo.election.LeadershipSelectorListener;
import com.apollo.thriftgen.ApolloConstants;
import com.apollo.thriftgen.SchedulerAdminService;
import com.apollo.thriftgen.SchedulerAgentService;
import org.apache.commons.cli.*;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.apollo.thriftgen.SchedulerConstants.*;

/**
 * Starts up a scheduler process.
 */
public class SchedulerBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerBootstrap.class);
    private static final boolean DEBUG = true;

    private static Scheduler scheduler;
    private static SchedulerAgentService.Processor agentProcessor;
    private static SchedulerAdminService.Processor adminProcessor;

    private static void setLoggingLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    public static void main(String[] args) {
        LOGGER.info("Scheduler bootstrap starting.");
        if (!DEBUG) {
            setLoggingLevel(Level.INFO);
        }
        // Set up command line options
        Option ipOpt = new Option("ip", true, "IP address to listen on.");
        Option adminPortOpt = new Option("adminport", true, "Port the admin service listens on.");
        Option agentPortOpt = new Option("agentport", true, "Port the agent service listens on.");
        Option quorumOpt = new Option("quorum", true, "The zookeeper quorum used for master election.");
        CommandLineParser parser = new DefaultParser();
        Options opts = new Options();
        opts.addOption(ipOpt);
        opts.addOption(adminPortOpt);
        opts.addOption(agentPortOpt);
        opts.addOption(quorumOpt);

        String ipStr = DEFAULT_SCHEDULER_SERVICE_IP;
        int adminPortN = DEFAULT_SCHEDULER_ADMIN_SERVICE_PORT;
        int agentPortN = DEFAULT_SCHEDULER_AGENT_SERVICE_PORT;
        String quorumStr = ApolloConstants.DEFAULT_QUORUM_STR;
        // Parse the command line args
        try {
            CommandLine cmd = parser.parse(opts, args);
            String testIp =  cmd.getOptionValue("ip");
            if (testIp != null) ipStr = testIp;
            String testAdminPort = cmd.getOptionValue("adminport");
            if (testAdminPort != null) adminPortN = Integer.parseInt(testAdminPort);
            String testAgentPort = cmd.getOptionValue("agentport");
            if (testAgentPort != null) agentPortN = Integer.parseInt(testAgentPort);
            String testQuorum =  cmd.getOptionValue("quorum");
            if (testQuorum != null) quorumStr = testQuorum;
        } catch (Exception ex) {
            printHelp(opts);
        }
        LOGGER.info("IP: " + ipStr + " ADMINPORT: " + adminPortN + " AGENTPORT: " + agentPortN + " QUORUM: " + quorumStr);
        final int adminPort = adminPortN; // need to be final, so had to do this. dont feel like making it better.
        final int agentPort = agentPortN; // need to be final, so had to do this. dont feel like making it better.

        // TODO: Set up command line args.

        // Start the scheduler state manager.
        SchedulerStateManager stateManager = new SchedulerStateManager();
        stateManager.start();

        // Create and start the scheduler thread.
        scheduler = new Scheduler(stateManager);
        scheduler.start();

        // Start the SchedulerAgentService server.
        agentProcessor = new SchedulerAgentService.Processor(scheduler);
        new Thread(() -> {
            try {
                LOGGER.info("Starting SchedulerAgentService server on port " + agentPort + ".");
                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(agentPort);
                TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(agentProcessor));
                server.serve();
            } catch (Exception ex) {
                LOGGER.error("SchedulerAgentService server died unexpectedly. Exiting.");
                LOGGER.error("REASON: " + ex.toString());
                System.exit(1);
            }
        }).start();

        // Start the SchedulerAdminService server.
        adminProcessor = new SchedulerAdminService.Processor(scheduler);
        new Thread(() -> {
            try {
                LOGGER.info("Starting SchedulerAdminService server on port " + adminPort + ".");
                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(adminPort);
                TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(adminProcessor));
                server.serve();
            } catch (Exception ex) {
                LOGGER.error("SchedulerAdminService server died unexpectedly. Exiting.");
                LOGGER.error("REASON: " + ex.toString());
                System.exit(1);
            }
        }).start();

        // Id for this leader: "<IP>,<ADMINPORT>,<AGENTPORT>,<SCHEDULERID>".
        String id = ipStr + "," + adminPort + "," + agentPort + "," + scheduler.getSchedulerID();

        // Start the leadership finder.
        LeadershipSelectorListener leaderFinder = new LeadershipSelectorListener(quorumStr, id, scheduler);
        leaderFinder.start();

        try {
            // Give up the main threads resources.
            Thread.currentThread().join();
            // Will never reach this point unless interrupted.
        } catch (InterruptedException e) {
            LOGGER.info("Scheduler shutting down.");
        }
    }

    private static void printHelp(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( AgentBootstrap.class.getSimpleName(), opts );
        System.exit(1);
    }
}
