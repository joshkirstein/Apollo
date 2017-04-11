package com.apollo.docker;

import com.google.common.base.Preconditions;
import org.json.JSONObject;

import java.util.Optional;

public class Container {
    private static final String ID_FIELD = "Id";
    private static final String NAME_FIELD = "Name";
    private static final String STATE_FIELD = "State";
    private static final String PID_FIELD = "Pid";
    private static final String NETWORK_SETTINGS_FIELD = "NetworkSettings";
    private static final String IPADDRESS_FIELD = "IPAddress";
    private static final String STATUS_FIELD = "Status";

    public enum Status {
        CREATED,
        RESTARTING,
        RUNNING,
        PAUSED,
        EXITED,
        DEAD;
        public static Status fromString(String str) {
            str = str.toUpperCase();
            for (Status st : Status.values()) {
                if (st.name().equals(str)) return st;
            }
            return null;
        }
    }

    private final JSONObject json;
    public final String id;
    public final String name;
    public final Optional<Long> pid;
    public final Optional<String> ipAddress;
    public final Status status;

    public Container(String jsonString) {
        Preconditions.checkNotNull(jsonString, "jsonString must not be null!");
        this.json = new JSONObject(jsonString);
        // Pull info out of root object
        id = json.getString(ID_FIELD);
        name = json.getString(NAME_FIELD);

        // Gather data out of Status object
        JSONObject stateObject = json.getJSONObject(STATE_FIELD);
        long pid = stateObject.getLong(PID_FIELD);
        if (pid == 0L) {
            this.pid = Optional.empty();
        } else {
            this.pid = Optional.of(pid);
        }

        // Gather data out of NetworkSettings object
        JSONObject networkObject = json.getJSONObject(NETWORK_SETTINGS_FIELD);
        String ipAddress = networkObject.getString(IPADDRESS_FIELD);
        if (ipAddress.isEmpty()) {
            this.ipAddress = Optional.empty();
        } else {
            this.ipAddress = Optional.of(ipAddress);
        }
        this.status = Status.fromString(stateObject.getString(STATUS_FIELD));
    }

    @Override
    public String toString() {
        return "Container(Name: " + name + ", ID: " + id + ", PID: " + pid + ", IP: " + ipAddress + ")";
    }
}
