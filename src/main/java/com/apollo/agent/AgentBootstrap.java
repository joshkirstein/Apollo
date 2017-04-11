package com.apollo.agent;

import ch.qos.logback.classic.Level;
import com.apollo.discovery.MasterDiscovery;
import com.apollo.docker.Docker;
import com.apollo.thriftgen.AgentService;
import com.apollo.thriftgen.ApolloConstants;
import org.apache.commons.cli.*;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.apollo.thriftgen.AgentConstants.*;

/**
 * Starts up an agent process.
 */
public class AgentBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentBootstrap.class);
    private static final boolean DEBUG = false;

    private static Docker docker;
    private static AgentServiceHandler agentServiceHandler;
    private static AgentService.Processor processor;

    private static void setLoggingLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    public static void main(String[] args) throws IOException {
        // TODO: flags, docker version, master detector, status update manager, agent thread
        LOGGER.info("Agent bootstrap starting.");
        if (!DEBUG) {
            setLoggingLevel(Level.INFO);
        }
        // Set up command line options
        Option ipOpt = new Option("ip", true, "IP address to listen on.");
        Option portOpt = new Option("port", true, "Port to listen on.");
        Option quorumOpt = new Option("quorum", true, "The zookeeper quorum used for master election.");
        Option dockerOpt = new Option("docker", true, "The location of the docker client binary.");
        CommandLineParser parser = new DefaultParser();
        Options opts = new Options();
        opts.addOption(ipOpt);
        opts.addOption(portOpt);
        opts.addOption(quorumOpt);
        opts.addOption(dockerOpt);

        String ipStr = DEFAULT_AGENT_SERVICE_IP;
        int portN = DEFAULT_AGENT_SERVICE_PORT;
        String quorumStr = ApolloConstants.DEFAULT_QUORUM_STR;
        String dockerStr = DEFAULT_DOCKER_LOCATION;
        // Parse the command line args
        try {
            CommandLine cmd = parser.parse(opts, args);
            String testIp =  cmd.getOptionValue("ip");
            if (testIp != null) ipStr = testIp;
            String testPort = cmd.getOptionValue("port");
            if (testPort != null) portN = Integer.parseInt(testPort);
            String testQuorum =  cmd.getOptionValue("quorum");
            if (testQuorum != null) quorumStr = testQuorum;
            String testDocker = cmd.getOptionValue("docker");
            if (testDocker != null) dockerStr = testDocker;
        } catch (Exception ex) {
            printHelp(opts);
        }
        LOGGER.info("IP: " + ipStr + " PORT: " + portN + " QUORUM: " + quorumStr + " DOCKER: " + dockerStr);
        final int port = portN; // need to be final, so had to do this. dont feel like making it better.

        // Set up masterdiscovery quorum string.
        MasterDiscovery.getSingleton().setQuorumString(quorumStr);

        // Create a docker instance, and verify version
        docker = new Docker(dockerStr);
        if (!docker.isValidVersion()) {
            LOGGER.error("Docker version " + docker.getVersion() + " not supported!");
            System.exit(1);
        } else {
            LOGGER.info("Docker version " + docker.getVersion());
        }

        // Start up the StatusUpdateManager
        StatusUpdateManager updateManager = new StatusUpdateManager();
        updateManager.play(); // initially updateManager starts as paused. Order doesn't matter of this op.
        updateManager.start();

        // Spin up agent service handler server.
        agentServiceHandler = new AgentServiceHandler(ipStr, port, docker, updateManager);
        processor = new AgentService.Processor(agentServiceHandler);
        new Thread(() -> {
            try {
                LOGGER.info("Starting AgentService server on port " + port + ".");
                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
                TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
                server.serve();
            } catch (Exception ex) {
                LOGGER.error("AgentService server died unexpectedly. Exiting.");
                LOGGER.error("REASON: " + ex.toString());
                System.exit(1);
            }
        }).start();

        // Start agent service background thread
        agentServiceHandler.start();

        try {
            // Give up the main threads resources.
            Thread.currentThread().join();
            // Will never reach this point unless interrupted.
        } catch (InterruptedException e) {
            LOGGER.info("Agent shutting down.");
        }
    }

    private static void printHelp(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( AgentBootstrap.class.getSimpleName(), opts );
        System.exit(1);
    }
}
