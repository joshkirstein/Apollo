include "Apollo.thrift"

namespace java com.apollo.thriftgen

const string DEFAULT_DOCKER_LOCATION = "/usr/local/bin/docker";
const string DEFAULT_AGENT_SERVICE_IP = "127.0.0.1";
const i32 DEFAULT_AGENT_SERVICE_PORT = 9893;

exception AgentTaskRegistrationException {
    1: string why,
}

exception AgentTaskKillException {
    1: string why,
}

exception AgentTaskPurgeException {
    1: string why,
}

service AgentService
{
    // just pings the agent to see if it's alive
    Apollo.Response ping(1: Apollo.SchedulerID scheduler),
    // gets the health status of the agent
    Apollo.Response getAgentHealth(1: Apollo.SchedulerID scheduler),
    // gets the health status of all the tasks this agent maintains
    Apollo.Response getTaskHealth(1: Apollo.SchedulerID scheduler),
    // registers a task descriptor
    Apollo.Response registerTask(1: Apollo.SchedulerID scheduler, 2: Apollo.TaskDescriptor task) throws (1: AgentTaskRegistrationException ex),
    // tells the agent to kill a task (agent is responsible for status update)
    Apollo.Response killTask(1: Apollo.SchedulerID scheduler, 2: Apollo.TaskID task) throws (1: AgentTaskKillException ex),
    // tells the agent to purge (kill, basically) all tasks on this machine
    // and not allow the task to be rescheduled on this machine for a small period of time
    Apollo.Response purge(1: Apollo.SchedulerID scheduler) throws (1: AgentTaskPurgeException ex),
    // kills the agent (may be called before a purge or after)
    // if called before a purge, timeout will be waited for tasks to be rescheduled
    // if called after a purge, everything should be okay
    Apollo.Response kill(1: Apollo.SchedulerID scheduler),
}
