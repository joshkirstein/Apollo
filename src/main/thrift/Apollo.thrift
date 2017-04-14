namespace java com.apollo.thriftgen


const string DEFAULT_QUORUM_STR = "127.0.0.1:2181";
const string LEADER_PATH = "/apollo/leader";

union Resource {
    1: double numCpus,
    2: i64 ramMb,
    3: i64 diskMb,
    4: string requestedPort,
}

struct FetcherURI {
    1: string locater,
}

// configuration regarding health checks... ping timeouts...
// num failures till kill....etc
struct HealthCheckConfiguration {

}

// Uniquely identify a networked machine.
struct MachineDescriptor {
    1: string id,
    2: string ip,
    3: i32 port,
}

// agent id struct just for typing
struct AgentID {
    1: string id,
}

// scheduler id struct just for typing
struct SchedulerID {
    1: string id,
}

// task id struct just for typing
struct TaskID {
    1: string id,
}

enum TaskStatus {
    STAGING, // 0, 1, 3, 4, 5 == orange
    STARTING, // 2 == green
    RUNNING, // 6, 7, 8, 9, 10 == orange
    KILLING,
    PURGING,
    RESTARTING,
    FINISHED,
    FAILED,
    KILLED,
    PURGED,
    LOST,
}

// states where the task is considered active and running
const set<TaskStatus> ACTIVE_STATES = [TaskStatus.STARTING,
                                        TaskStatus.RUNNING,
                                        TaskStatus.KILLING,
                                        TaskStatus.PURGING,
                                        TaskStatus.RESTARTING,]

// states where the task is considered dead and needs to be
// restarted
const set<TaskStatus> TERMINAL_STATES = [TaskStatus.FINISHED,
                                            TaskStatus.FAILED,
                                            TaskStatus.KILLED,
                                            TaskStatus.LOST,
                                            TaskStatus.PURGED,
                                            TaskStatus.STAGING,]
struct StatusUpdate {
    1: i64 timestamp,
    2: TaskStatus status,
    3: optional string message,
    4: optional SchedulerID scheduler,
}

// describes a task
struct TaskDescriptor {
    1: string name,
    2: string id,
    3: FetcherURI urlLocater,
    4: set<Resource> resources,
}

// an actual task
struct Task {
    // description of this task
    1: TaskDescriptor descriptor,
    // the agent this task is assigned to
    2: AgentID agentId,
    // number of failures this task has had
    3: i32 numFailures,
    // current status of the task
    4: TaskStatus status,
    // list of status updates over the lifetime of this task
    5: list<StatusUpdate> statusUpdates,
}

struct Agent {
    1: MachineDescriptor info,
    2: i64 lastPingSuccess,
    3: i64 lastPingAttempt,
}

struct Scheduler {
    1: MachineDescriptor info,
    2: HealthCheckConfiguration healthInfo,
}

// the response code of a service call
enum ResponseCode {
    INVALID_REQUEST,
    OK,
    ERROR,
    WARNING,
}

struct AgentRegisterResult {
    1: AgentID id,
}

struct SchedulerRegisterResult {
    1: TaskID id,
}

struct SchedulerVerifyTasksResult {
    1: list<TaskID> verifiedTasks,
}

struct SchedulerGetTaskPortsResult {
    1: list<string> portStrings,
}

struct SchedulerState {
    1: list<Task> taskList,
    2: list<Agent> registeredAgents,
}

struct SchedulerGetStateResult {
    1: SchedulerState state,
}

// the result of a service call
union Result {
    1: AgentRegisterResult agentRegisterResult,
    2: SchedulerRegisterResult schedulerRegisterResult,
    3: SchedulerVerifyTasksResult schedulerVerifyTasksResult,
    4: SchedulerGetTaskPortsResult schedulerGetTaskPortsResult,
    5: SchedulerGetStateResult schedulerGetStateResult,
}

// describes a response from a service call
struct Response {
    1: ResponseCode responseCode,
    2: optional Result result,
}
