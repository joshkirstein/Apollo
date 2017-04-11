package com.apollo.executor;

import com.apollo.agent.StatusUpdateManager;
import com.apollo.docker.Container;
import com.apollo.docker.Docker;
import com.apollo.docker.Image;
import com.apollo.fetcher.HttpFileFetcher;
import com.apollo.thriftgen.StatusUpdate;
import com.apollo.thriftgen.TaskDescriptor;
import com.apollo.thriftgen.TaskID;
import com.apollo.thriftgen.TaskStatus;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

// executes and monitors a single task
public class TaskExecutor extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    private static final long SLEEP_MS = 1000 * 1; // checks every second
    private final TaskID id;
    private final TaskDescriptor taskDescriptor;
    private final Docker docker;
    private final StatusUpdateManager updateManager;
    private TaskStatus currentStatus = null;
    private Image image = null;
    private Container container = null;
    private boolean killTask = false;

    public TaskExecutor(TaskDescriptor taskDescriptor, Docker docker, StatusUpdateManager updateManager) {
        super("TaskExecutor-" + taskDescriptor.getName());
        this.taskDescriptor = Preconditions.checkNotNull(taskDescriptor, "taskDescriptor must not be null!");
        this.id = new TaskID(taskDescriptor.id);
        this.docker = Preconditions.checkNotNull(docker, "docker must not be null!");
        this.updateManager = Preconditions.checkNotNull(updateManager, "updateManager must not be null!");
    }

    public synchronized void killTask() {
        killTask = true;
    }

    @Override
    public void run() {
        LOGGER.info("TaskExecutor started for task [taskID=" + taskDescriptor.id + "].");
        // fetch, install and run the headless docker container
        // then monitor status updates, etc. As long as this task is executing, this
        // executor should be alive.
        updateTaskStatus(TaskStatus.STARTING);

        // Step 1: fetch image
        String urlStr = taskDescriptor.getUrlLocater().getLocater();
        URL url = null;
        File imageFile = null;
        try {
            url = new URL(urlStr);
            imageFile = HttpFileFetcher.getSingleton().fetch(url);
        } catch (Exception ex) {
            LOGGER.error("Failed to fetch image for task [taskID=" + taskDescriptor.id + "].");
            // this is an unrecoverable failure... maybe we should have a different state for this?
            // for now, we'll have a timeout for how long a task can be in the failed state. If it can't be
            // restarted until then, then the task will be moved to another machine. After a certain
            // amount of failures, the task will be auto-killed and won't be allowed to be restarted.
            updateTaskStatus(TaskStatus.FAILED, ex.getMessage());
            return;
        }

        try {
            // Step 2: load image
            assert imageFile != null;
            image = docker.load(imageFile);

            // Step 3: run image
            assert image != null;
            container = docker.run(image.id);
        } catch (Exception ex) {
            updateTaskStatus(TaskStatus.FAILED, ex.getMessage());
            return;
        }

        LOGGER.info("TaskExecutor started running task [taskID=" + taskDescriptor.id + "].");
        updateTaskStatus(TaskStatus.RUNNING);

        while (true) {
            // Step 0: Check if we were asked to kill this task.
            if (killTask) {
                // try and remove container if it exists.
                try {
                    String inspect = docker.inspect(container.name);
                    Container inspected = new Container(inspect);
                    docker.rm(inspected.id, true);
                } catch (Exception ex) { }
                updateTaskStatus(TaskStatus.KILLED);
                break;
            }

            // Step 1: Check if container is finished or dead.
            String inspect = null;
            try {
                inspect = docker.inspect(container.name);
            } catch (Exception ex) {
                updateTaskStatus(TaskStatus.FAILED, ex.getMessage());
                break;
            }
            Container inspected = new Container(inspect);
            if (inspected.status == Container.Status.EXITED) {
                LOGGER.info("TaskExecutor for task [taskID=" + taskDescriptor.id + "] found the container finished!");
                updateTaskStatus(TaskStatus.FINISHED);
                break;
            } else if (inspected.status != Container.Status.RUNNING) {
                LOGGER.info("TaskExecutor for task [taskID=" + taskDescriptor.id + "] found the container dead! Restarting.");
                updateTaskStatus(TaskStatus.FAILED);
                // try and restart it...
                try {
                    docker.rm(inspected.id, true);
                    container = docker.run(image.id);
                } catch (Exception ex) {
                    break;
                }
                // if restarted successfully, we update status to running
                updateTaskStatus(TaskStatus.RUNNING);
            }

            // Step 2: Sleep for a little bit to give up
            try {
                Thread.sleep(SLEEP_MS);
            } catch (InterruptedException e) {
                LOGGER.error("TaskExecutor for task [taskID=" + taskDescriptor.id + "] interrupted!");
                continue;
            }
        }
    }

    private void updateTaskStatus(TaskStatus next) {
        updateTaskStatus(next, null);
    }

    private void updateTaskStatus(TaskStatus next, String message) {
        Preconditions.checkNotNull(next, "next must not be null!");
        LOGGER.info("Task [taskID=" + taskDescriptor.id + "] trying to change status to " + next);
        StatusUpdate update = new StatusUpdate(System.currentTimeMillis(), next);
        if (message != null) update.setMessage(message);
        currentStatus = next;
        updateManager.sendUpdate(id, update);
    }
}
