package com.apollo.docker;

import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Container {
    private static final String ID_FIELD = "Id";
    private static final String NAME_FIELD = "Name";
    private static final String STATE_FIELD = "State";
    private static final String PID_FIELD = "Pid";
    private static final String NETWORK_SETTINGS_FIELD = "NetworkSettings";
    private static final String IPADDRESS_FIELD = "IPAddress";
    private static final String STATUS_FIELD = "Status";
    private static final String PORTS_FIELD = "Ports";
    private static final String TCP_FIELD = "/tcp";
    private static final String HOSTIP_FIELD = "HostIp";
    private static final String HOSTPORT_FIELD = "HostPort";

    public enum Status {
        CREATED,
        RESTARTING,
        RUNNING,
        PAUSED,
        EXITED,
        REMOVING,
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
    public final List<String> ports;

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

        ports = new ArrayList<>();
        JSONObject portsObject = networkObject.optJSONObject(PORTS_FIELD);
        if (portsObject != null) {
            for (String key : portsObject.keySet()) {
                String port = key.replace(TCP_FIELD, "");
                JSONArray arr = portsObject.getJSONArray(key);
                JSONObject portListing = arr.getJSONObject(0);
                String hostIp = portListing.getString(HOSTIP_FIELD);
                String hostPort = portListing.getString(HOSTPORT_FIELD);
                ports.add(hostIp + ":" + hostPort + "->" + port);
            }
        }
    }

    @Override
    public String toString() {
        return "Container(Name: " + name + ", ID: " + id + ", PID: " + pid + ", IP: " + ipAddress + ")";
    }
}
