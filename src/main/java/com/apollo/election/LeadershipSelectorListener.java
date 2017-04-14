package com.apollo.election;

import com.apollo.scheduler.Scheduler;
import com.apollo.thriftgen.ApolloConstants;
import com.google.common.base.Preconditions;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeadershipSelectorListener extends LeaderSelectorListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeadershipSelectorListener.class);

    private final Scheduler scheduler;
    private final CuratorFramework client;
    private final LeaderSelector selector;

    public LeadershipSelectorListener(String quorumStr, String id, Scheduler scheduler) {
        Preconditions.checkNotNull(quorumStr, "quorumStr must not be null!");
        Preconditions.checkNotNull(id, "id must not be null!");
        this.scheduler = scheduler; // scheduler can be null
        this.client = CuratorFrameworkFactory.newClient(quorumStr, new ExponentialBackoffRetry(1000, 3));
        this.selector = new LeaderSelector(client, ApolloConstants.LEADER_PATH, this);
        LOGGER.info("Starting ZooKeeper client.");
        client.start();
        try {
            client.blockUntilConnected();
        } catch (InterruptedException ex) {
            LOGGER.error("Error connecting to ZooKeeper quorum [message=" + ex.getMessage() + "].");
            System.exit(1);
        }
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(ApolloConstants.LEADER_PATH, new byte[0]);
        } catch (KeeperException.NodeExistsException ex) {
            LOGGER.info("Leader path already exists [path=" + ApolloConstants.LEADER_PATH + "].");
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Error making sure ZooKeeper leader path exists [path=" + ApolloConstants.LEADER_PATH + "] with message [message=" + ex.getMessage() + "].");
            System.exit(1);
        }
        selector.setId(id);
    }

    public void start() {
        LOGGER.info("Starting LeadershipSelectorListener.");
        selector.start();
    }

    public String getLeaderId() {
        try {
            return selector.getLeader().getId();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        LOGGER.info("Gained leadership! [id=" + this.getLeaderId() + "]");

        // Tell scheduler we have leadership
        scheduler.takeLeadership();

        try {
            // Give up the main threads resources.
            Thread.currentThread().join();
            // Will never reach this point unless interrupted.
        } catch (InterruptedException ex) {
            LOGGER.info("Scheduler shutting down.");
            System.exit(1);
        }
    }
}
