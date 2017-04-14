package com.apollo.scheduler;

import com.apollo.storage.DistributedLog;
import com.apollo.thriftgen.*;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SchedulerStateManager extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerStateManager.class);

    private final Object SCHEDULER_LOCK = new Object();
    private SchedulerState state = new SchedulerState();

    public SchedulerStateManager() {
        super("SchedulerStateManager");
    }

    @Override
    public void run() {
        LOGGER.info("SchedulerStateManager thread started.");
        // periodically take a snapshot and backup to distributed fs.
    }

    public void replay() {
        LOGGER.info("Loading SchedulerState from snapshot!");
        synchronized (SCHEDULER_LOCK) {
            SchedulerState snapshot = DistributedLog.readSnapshot();
            if (snapshot != null) {
                state = snapshot;
                List<Agent> agents = snapshot.getRegisteredAgents();
                if (agents != null) {
                    LOGGER.info("Loaded " + agents.size() + " agents.");
                }
                List<Task> tasks = snapshot.getTaskList();
                if (tasks != null) {
                    LOGGER.info("Loaded " + tasks.size() + " tasks.");
                }
            }
        }
    }

    public SchedulerState getSchedulerState() {
        synchronized (SCHEDULER_LOCK) {
            return state.deepCopy();
        }
    }

    // return a snapshot of the agents when this function is called. (it's a clone)
    // this is read only.
    public List<Agent> getAgents() {
        List<Agent> ret = new ArrayList<>();
        synchronized (SCHEDULER_LOCK) {
            if (state.getRegisteredAgents() != null) {
                for (Agent a : state.getRegisteredAgents()) {
                    ret.add(a);
                }
            }
        }
        return ret;
    }

    public List<Task> getTasks() {
        List<Task> ret = new ArrayList<>();
        synchronized (SCHEDULER_LOCK) {
            if (state.getTaskList() != null) {
                for (Task a : state.getTaskList()) {
                    ret.add(a);
                }
            }
        }
        return ret;
    }


    public SchedulerVerifyTasksResult verifyTasks(AgentID id, List<TaskID> tasks) {
        List<TaskID> verifiedTasks = new ArrayList<>();

        synchronized (SCHEDULER_LOCK) {
            List<Task> allTasks = getTasks();
            for (Task t : allTasks) {
                TaskID tid = new TaskID(t.getDescriptor().id);
                if (tasks.contains(tid) && t.getAgentId().equals(id)) {
                    verifiedTasks.add(tid);
                }
            }
        }

        return new SchedulerVerifyTasksResult(verifiedTasks);
    }

    public void registerTask(Task task) {
        Preconditions.checkNotNull(task, "task must not be null!");
        synchronized (SCHEDULER_LOCK) {
            List<Task> tasks = getTasks();
            for (Task t : tasks) {
                if (t.getDescriptor().getId().equals(task.getDescriptor().getId())) {
                    LOGGER.error("Task already registered [taskID=" + t.getDescriptor().getId() + "].");
                    return;
                }
            }
            state.addToTaskList(task);
            DistributedLog.writeSnapshot(state);
        }
    }

    public static class TaskAssignment {
        public MachineDescriptor agent;
        public TaskDescriptor task;
        public TaskAssignment(MachineDescriptor agent, TaskDescriptor task) {
            this.agent = agent;
            this.task = task;
        }
    }

    public List<TaskAssignment> assignTasks() {
        List<TaskAssignment> assignments = new ArrayList<>();
        synchronized (SCHEDULER_LOCK) {
            // first get a list of all live agents.
            // then get a list of all unassigned tasks.
            // then match randomly.
            List<Agent> agents = getAgents();
            List<Task> tasks = getTasks();

            // get all valid agents
            agents = agents.stream().filter((agent) -> {
                long currentTime = System.currentTimeMillis();
                long lastPing = agent.lastPingSuccess;
                return (currentTime - lastPing) < Scheduler.PING_INTERVAL;
            }).collect(Collectors.toList());

            // shuffle agents
            Collections.shuffle(agents);

            final List<Agent> finAgents = agents;

            // get all unassigned tasks and failed tasks, reassign them!
            // TODO: Get tasks that dont have a valid agent assigned.
            // TODO: HANDLE FAILED AND FINISHED TASKS
            tasks = tasks.stream().filter((task) -> {
                // if not assigned an agent
                boolean needsAssign = task.getAgentId() == null;
                // or assigned an agent that doesnt exist....
                if (task.getAgentId() != null) {
                    boolean found = false;
                    for (Agent agent : finAgents) {
                        if (agent.info.getId().equals(task.getAgentId().id)) {
                            found = true;
                        }
                    }
                    if (!found) needsAssign = true;
                }
                return needsAssign && task.getStatus() != TaskStatus.KILLED;
            }).collect(Collectors.toList());

            if (tasks.size() != 0 && agents.size() != 0) {
                LOGGER.info("Assigning " + tasks.size() + " tasks to some of the " + agents.size() + " agents.");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    int agentIdx = i % agents.size();
                    Agent assigned = agents.get(agentIdx);
                    assignAgentToTask(new TaskID(task.getDescriptor().id), new AgentID(assigned.getInfo().id), false);
                    assignments.add(new TaskAssignment(assigned.getInfo(), task.getDescriptor()));
                }
                DistributedLog.writeSnapshot(state);
            }
        }
        return assignments;
    }

    public Agent getOwner(TaskID tid) {
        Preconditions.checkNotNull(tid, "tid must not be null!");
        synchronized (SCHEDULER_LOCK) {
            if (state.getTaskList() != null) {
                for (Task task : state.getTaskList()) {
                    if (task.getDescriptor().id.equals(tid.id)) {
                        AgentID aid = task.getAgentId();
                        if (aid != null) {
                            if (state.getRegisteredAgents() != null) {
                                for (Agent agent : state.getRegisteredAgents()) {
                                    if (agent.info.getId().equals(aid.id)) {
                                        return agent;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // updates all the tasks belonging to this agent as "lost"
    public void updateLostTasks(Agent agent) {
        Preconditions.checkNotNull(agent, "agent must not be null!");
        synchronized (SCHEDULER_LOCK) {
            AgentID agentID = new AgentID(agent.getInfo().id);
            if (state.getTaskList() != null) {
                for (Task task : state.getTaskList()) {
                    if (task.getAgentId() != null && task.getAgentId().equals(agentID)) {
                        TaskID tid = new TaskID(task.getDescriptor().id);
                        StatusUpdate lostUpdate = new StatusUpdate(System.currentTimeMillis(), TaskStatus.LOST);
                        // add the task update
                        handleTaskUpdate(agentID, tid, lostUpdate, false);
                        // unassign agent from this task
                        assignAgentToTask(tid, null, false);
                    }
                }
                DistributedLog.writeSnapshot(state);
            }
        }
    }

    public void assignAgentToTask(TaskID tid, AgentID agent, boolean snapshot) {
        Preconditions.checkNotNull(tid, "tid must not be null!");
        // agent may be null (to unassign a task)
        synchronized (SCHEDULER_LOCK) {
            if (state.getTaskList() != null) {
                for (Task task : state.getTaskList()) {
                    if (task.getDescriptor().id.equals(tid.id)) {
                        LOGGER.info("Assigning task [taskID=" + tid + "] to agent " + agent);
                        task.setAgentId(agent);
                        if (snapshot) {
                            DistributedLog.writeSnapshot(state);
                        }
                        return;
                    }
                }
            }
        }
    }

    // handles a task update by a given agent. if the agent is not responsible for the task,
    // ignore it and return false. otherwise true.
    public boolean handleTaskUpdate(AgentID aid, TaskID tid, StatusUpdate update, boolean snapshot) {
        Preconditions.checkNotNull(aid, "aid must not be null!");
        Preconditions.checkNotNull(tid, "tid must not be null!");
        Preconditions.checkNotNull(update, "update must not be null!");
        synchronized (SCHEDULER_LOCK) {
            if (state.getTaskList() != null) {
                for (Task task : state.getTaskList()) {
                    if (task.getDescriptor().id.equals(tid.id) &&
                            task.getAgentId().equals(aid)) {
                        LOGGER.info("Updating task status for [taskID=" + tid + "] to status " + update.getStatus());
                        task.addToStatusUpdates(update);
                        task.setStatus(update.getStatus());
                        if (snapshot) {
                            DistributedLog.writeSnapshot(state);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // adds the given machine as an agent to our scheduler state.
    // if it already exists, we return the Agent object corresponding to it.
    public Agent addAgent(MachineDescriptor descriptor) {
        Preconditions.checkNotNull(descriptor, "descriptor must not be null!");
        // TODO: do a distributed transaction with log file.
        String id = UUID.randomUUID().toString();
        AgentID agentID = new AgentID(id);
        boolean contains = false;
        Agent ret = null;
        synchronized (SCHEDULER_LOCK) {
            if (state.getRegisteredAgents() != null) {
                for (Agent a : state.getRegisteredAgents()) {
                    if (a.getInfo().ip.equals(descriptor.ip) &&
                            a.getInfo().port == descriptor.port) {
                        contains = true;
                        ret = a;
                    }
                }
            }
        }
        if (contains) {
            return ret;
        }
        descriptor.setId(id);
        ret = new Agent(descriptor, 0l, 0l);
        synchronized (SCHEDULER_LOCK) {
            state.addToRegisteredAgents(ret);
            DistributedLog.writeSnapshot(state);
        }
        return ret;
    }
}
