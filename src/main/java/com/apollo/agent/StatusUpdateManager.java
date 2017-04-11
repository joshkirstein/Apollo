package com.apollo.agent;

import com.apollo.discovery.MasterDiscovery;
import com.apollo.thriftgen.AgentID;
import com.apollo.thriftgen.SchedulerAgentService;
import com.apollo.thriftgen.StatusUpdate;
import com.apollo.thriftgen.TaskID;
import com.apollo.timing.Clock;
import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class StatusUpdateManager extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusUpdateManager.class);
    private static final long TIMEOUT_MILLIS = 1000l * 30l;


    private enum QueueItemState {
        INITIALIZED,
        IN_FLIGHT,
        TIMEOUT,
        COMPLETE
    }

    private class QueueItem {
        private final Logger logger = LoggerFactory.getLogger(QueueItem.class);
        public class QueueItemCallback implements AsyncMethodCallback<SchedulerAgentService.AsyncClient.updateTaskStatus_call> {
            @Override
            public void onComplete(SchedulerAgentService.AsyncClient.updateTaskStatus_call updateTaskStatus_call) {
                logger.debug("Received an ACK for the queue item for task [taskID=" + task + "].");
                // TODO: maybe check response code?
                synchronized (ITEM_LOCK) {
                    itemState = QueueItemState.COMPLETE;
                }
            }
            @Override
            public void onError(Exception e) {
                // TODO: make a delay for sending after a timeout?
                logger.debug("Received a TIMEOUT for the queue item for task [taskID=" + task + "].");
                synchronized (ITEM_LOCK) {
                    itemState = QueueItemState.TIMEOUT;
                }
            }
        }
        private final Object ITEM_LOCK = new Object();
        private final Clock timeout;
        private TaskID task;
        private StatusUpdate update;
        private QueueItemState itemState;
        private SchedulerAgentService.AsyncClient client;
        public QueueItem(TaskID task, StatusUpdate update) {
            this.task = Preconditions.checkNotNull(task, "task must not be null!");
            this.update = Preconditions.checkNotNull(update, "update must not be null!");
            this.itemState = QueueItemState.INITIALIZED;
            this.timeout = new Clock(TimeUnit.MILLISECONDS, TIMEOUT_MILLIS);
        }

        public boolean hasTimeout() {
            boolean timedOut = false;
            synchronized (ITEM_LOCK) {
                timedOut = (timeout.isUp()) &&
                        (itemState == QueueItemState.IN_FLIGHT || itemState == QueueItemState.TIMEOUT);
            }
            return timedOut;
        }

        public void sendUpdate() {
            if (itemState == QueueItemState.IN_FLIGHT || itemState == QueueItemState.COMPLETE) {
                logger.error("Can't send an update when it's already in the state=" + itemState + ".");
                return;
            }
            if (itemState == QueueItemState.TIMEOUT && !hasTimeout()) {
                logger.error("Can't send an update when we're still waiting for timeout.");
                return;
            }
            logger.debug("Sending an update for a queue item for task [taskID=" + task + "].");
            synchronized (ITEM_LOCK) {
                try {
                    client = new SchedulerAgentService.AsyncClient(
                            new TBinaryProtocol.Factory(), new TAsyncClientManager(),
                            new TNonblockingSocket(MasterDiscovery.getSingleton().getMasterIP(), MasterDiscovery.getSingleton().getMasterPort())
                    );
                    // TODO: set client timeout correctly. Right now that's 30 seconds I think.
                    client.setTimeout(TIMEOUT_MILLIS);
                    timeout.restart();
                    client.updateTaskStatus(agentID, task, update, new QueueItemCallback());
                    itemState = QueueItemState.IN_FLIGHT;
                } catch (IOException | TException ex) {
                    itemState = QueueItemState.TIMEOUT;
                }
            }
        }
    }

    private LinkedList<QueueItem> statusList = new LinkedList<>();
    private AgentID agentID;
    private boolean sendUpdates = false;

    public StatusUpdateManager() {
        super("StatusUpdateManager");
    }

    public synchronized void setAgentID(AgentID agentID) {
        this.agentID = Preconditions.checkNotNull(agentID, "agentID must not be null!");
    }

    public synchronized AgentID getAgentID() {
        return agentID;
    }

    // queues the update to be sent. the updatemanager will send this update,
    // and wait for an ACK (a.k.a RPC response). If it doesn't receive it then
    // it will try and resend just keep trying to send (no backoff).
    public synchronized void sendUpdate(TaskID task, StatusUpdate update) {
        Preconditions.checkNotNull(task, "task must not be null!");
        Preconditions.checkNotNull(update, "update must not be null!");
        statusList.add(new QueueItem(task, update));
    }

    // resumes running of the update managers. all queued status updates
    // will be pushed (according to timeout and backoff)
    public synchronized void play() {
        LOGGER.info("StatusUpdateManager played.");
        sendUpdates = true;
        flushUpdates();
    }

    // pauses running of the update manager. any status updates
    // will be queued until played again.
    public synchronized void pause() {
        LOGGER.info("StatusUpdateManager paused.");
        sendUpdates = false;
    }

    public synchronized void flushUpdates() {
        // flushes updates if they haven't been sent/have timed out
        // otherwise leaves the update alone until timeout.
        Iterator<QueueItem> it = statusList.iterator();
        while (it.hasNext()) {
            QueueItem cur = it.next();
            if (cur.itemState == QueueItemState.COMPLETE) {
                it.remove();
            } else if (cur.itemState == QueueItemState.INITIALIZED ||
                    (cur.itemState == QueueItemState.TIMEOUT && cur.hasTimeout())) {
                cur.sendUpdate();
            }
        }
    }

    @Override
    public void run() {
        LOGGER.info("StatusUpdateManager started.");
        while (true) {
            boolean update = false;
            synchronized (this) {
                update = sendUpdates;
            }
            if (update) {
                flushUpdates();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                LOGGER.error("StatusUpdateManager thread interrupted!");
                continue;
            }
        }
    }
}
