include "Apollo.thrift"

namespace java com.apollo.thriftgen

const string DEFAULT_SCHEDULER_SERVICE_IP = "127.0.0.1";
const i32 DEFAULT_SCHEDULER_AGENT_SERVICE_PORT = 6666;
const i32 DEFAULT_SCHEDULER_ADMIN_SERVICE_PORT = 43594;

struct SchedulerState {
    1: list<Apollo.Task> taskList,
    2: list<Apollo.Agent> registeredAgents,
}

exception AgentRegistrationException {
    1: string why,
}

exception SchedulerTaskStatusUpdateException {
    1: string why,
}

exception SchedulerTaskStatusRequestException {
    1: string why,
}

exception SchedulerAgentInfoRequestException {
    1: string why,
}

exception SchedulerSnapshotException {
    1: string why,
}

exception SchedulerTaskRegistrationException {
    1: string why,
}

exception SchedulerTaskKillException {
    1: string why,
}

service SchedulerAgentService {
    // verify the tasks for this agent are correct. returns a list of assigned tasks.
    Apollo.Response verifyTasks(1: Apollo.AgentID id, 2: list<Apollo.TaskID> tasks),
    // registers the agent that's connecting. Since we can't get connection info from a RPC,
    // the caller will have to provide connection info (hench the machine descriptor. The ID
    // is ignored and will be provided by the Scheduler.
    Apollo.Response registerAgent(1: Apollo.MachineDescriptor thiz) throws (1: AgentRegistrationException ex),
    // received from an agent, updates the specified tasks status
    // according to the given update. agent should not consider
    // the update as recorded until this method returns successfully.
    Apollo.Response updateTaskStatus(1: Apollo.AgentID id, 2: Apollo.TaskID task, 3: Apollo.StatusUpdate update) throws (1: SchedulerTaskStatusUpdateException ex),
}

service SchedulerAdminService {
    // registers a task descriptor to the scheduler for running. The ID that's provided is
    // ignored and the scheduler ends up assigning its own.
    Apollo.Response registerTask(1: Apollo.TaskDescriptor task) throws (1: SchedulerTaskRegistrationException ex),
    // kills the task
    Apollo.Response killTask(1: Apollo.TaskID task) throws (1: SchedulerTaskKillException ex),
    Apollo.Response getAllAgentInfo() throws (1: SchedulerAgentInfoRequestException ex),
    // gets status/health of the specified task
    Apollo.Response getTaskHealth(1: Apollo.TaskID task) throws (1: SchedulerTaskStatusRequestException ex),
    // gets status/health of all tasks
    Apollo.Response getAllTaskHealth() throws (1: SchedulerTaskStatusRequestException ex),
    // forces the scheduler to take a snapshot and store it in distributed log
    Apollo.Response takeSnapshot() throws (1: SchedulerSnapshotException ex),
    // kills the scheduler (can be done abruptly)
    Apollo.Response kill(),
}
