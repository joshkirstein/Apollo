package com.apollo.discovery;

import com.apollo.election.LeadershipSelectorListener;
import com.apollo.thriftgen.ApolloConstants;
import com.apollo.thriftgen.SchedulerConstants;
import com.apollo.thriftgen.SchedulerID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MasterDiscovery extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterDiscovery.class);
    private static MasterDiscovery SingletonInstance;
    // Master sleeps for 50 ms.
    private static final long MASTER_DISCOVERY_SLEEP_MILLIS = 50l;

    private String cachedMasterIp = SchedulerConstants.DEFAULT_SCHEDULER_SERVICE_IP;
    private int cachedMasterPort = SchedulerConstants.DEFAULT_SCHEDULER_AGENT_SERVICE_PORT;
    private String quorumStr = ApolloConstants.DEFAULT_QUORUM_STR;
    private SchedulerID cachedMasterId;
    private final List<Runnable> callbacks = new ArrayList<>();

    public MasterDiscovery() {
        super("MasterDiscovery");
    }

    public static synchronized MasterDiscovery getSingleton() {
        if (SingletonInstance == null) {
            SingletonInstance = new MasterDiscovery();
            SingletonInstance.start();
        }
        return SingletonInstance;
    }

    public synchronized void setQuorumString(String quorumStr) {
        this.quorumStr = quorumStr;
    }

    public void run() {
        LOGGER.info("MasterDiscovery thread started.");
        LeadershipSelectorListener listener = new LeadershipSelectorListener(quorumStr, "", null);
        while (true) {
            String id = listener.getLeaderId();
            if (id != null && !id.equals("")) {
                String[] spl = id.split(",");
                String ip = spl[0];
                int port = Integer.parseInt(spl[2]);
                SchedulerID schid = new SchedulerID(spl[3]);
                synchronized (this) {
                    boolean diff = false;
                    diff |= !cachedMasterIp.equals(ip);
                    diff |= cachedMasterPort != port;
                    diff |= cachedMasterId == null || !cachedMasterId.equals(schid);
                    if (diff) {
                        LOGGER.info("Found a new master [ip=" + ip + ",port=" + port + ",id=" + schid + "]!");
                        cachedMasterIp = ip;
                        cachedMasterPort = port;
                        cachedMasterId = schid;
                        fire();
                    }
                }
            }
            try {
                Thread.sleep(MASTER_DISCOVERY_SLEEP_MILLIS);
            } catch (InterruptedException ex) {
                LOGGER.error("MasterDiscovery thread interrupted!");
                continue;
            }
        }
    }

    public synchronized String getMasterIP() {
        return cachedMasterIp;
    }

    public synchronized int getMasterPort() {
        return cachedMasterPort;
    }

    public synchronized SchedulerID getMasterID() {
        return cachedMasterId;
    }

    public synchronized void registerCallback(Runnable run) {
        callbacks.add(run);
    }

    public synchronized void fire() {
        for (Runnable run : callbacks) {
            run.run();
        }
    }
}
