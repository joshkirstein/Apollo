package com.apollo.docker;

import com.apollo.process.ProcessUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Utility class for interfacing with Docker.
 * TODO: Add process timeouts.
 */
public class Docker {
    public static final Version MINIMUM_SUPPORTED_VERSION = new Version(1,13,0);

    private static final String DOCKER_PROCESS = "docker";
    private static final String DOCKER_PS = "ps";
    private static final String DOCKER_ALL_MOD = "--all";
    private static final String DOCKER_INSPECT = "inspect";
    private static final String DOCKER_VERSION_MOD = "--version";
    private static final String DOCKER_KILL = "kill";
    private static final String DOCKER_SIGNAL_MOD = "--signal";
    private static final String DOCKER_REMOVE = "rm";
    private static final String DOCKER_FORCE_MOD = "-f";
    private static final String DOCKER_STOP = "stop";
    private static final String DOCKER_TIMEOUT_MOD = "-t";
    private static final String DOCKER_RUN = "run";
    private static final String DOCKER_DETACHED_MOD = "-d";
    private static final String DOCKER_LOAD = "load";

    private final String dockerPath;

    // TODO: Allow for specifying socket/configeration.
    public Docker(String dockerPath) {
        this.dockerPath = Preconditions.checkNotNull(dockerPath, "dockerPath must not be null!");
    }

    // start, images
    public Container run(String image) {
        Preconditions.checkNotNull(image, "image must not be null!");
        // TODO: allow volume mounts, ports, hostnames, entrypoints (for specified commands),
        // environment variables, environment files, cpu shares, memory, devices, ipaddress, etc
        List<String> commands = new ArrayList<>();
        commands.add(DOCKER_PROCESS);
        commands.add(DOCKER_RUN);
        commands.add(DOCKER_DETACHED_MOD);
        commands.add("-P"); // random ports, TODO: remove
        commands.add(image);
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            List<String> res = ProcessUtils.runProcess(pb);
            String inspect = inspect(res.get(0));
            return new Container(DockerUtils.formatDockerJson(inspect));
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error running run command.", ex);
        }
    }

    // Pipes the file to load
    public Image load(File image) {
        Preconditions.checkNotNull(image, "image must not be null!");
        byte[] fileData;
        try {
            fileData = FileUtils.readFileToByteArray(image);
        } catch (IOException ex) {
            throw new RuntimeException("Problem reading from image file!", ex);
        }
        ProcessBuilder pb = new ProcessBuilder(DOCKER_PROCESS, DOCKER_LOAD);
        try {
            List<String> res = ProcessUtils.runProcess(pb, fileData);
            String hash = DockerUtils.parseImageHash(res.get(0));
            String inspect = inspect(hash);
            return new Image(inspect);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error running load command.", ex);
        }
    }

    // kills a container
    public void kill(String container, int signal) {
        Preconditions.checkNotNull(container, "container must not be null!");
        ProcessBuilder pb = new ProcessBuilder(DOCKER_PROCESS, DOCKER_KILL, DOCKER_SIGNAL_MOD + "=" + signal, container);
        try {
            ProcessUtils.runProcess(pb);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error running kill command.", ex);
        }
    }

    // removes a container, basically kill and delete
    public void rm(String container, boolean force) {
        Preconditions.checkNotNull(container, "container must not be null!");
        List<String> commands = new ArrayList<>();
        commands.add(DOCKER_PROCESS);
        commands.add(DOCKER_REMOVE);
        if (force) {
            commands.add(DOCKER_FORCE_MOD);
        }
        commands.add(container);
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            ProcessUtils.runProcess(pb);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error running rm command.", ex);
        }
    }

    // stops a container, basically pause
    public void stop(String container, int timeout, boolean remove) {
        Preconditions.checkNotNull(container, "container must not be null!");
        Preconditions.checkArgument(timeout >= 0, "timeout must not be negative!");
        ProcessBuilder pb = new ProcessBuilder(DOCKER_PROCESS, DOCKER_STOP, DOCKER_TIMEOUT_MOD + timeout, container);
        try {
            ProcessUtils.runProcess(pb);
            if (remove) {
                rm(container, true);
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Error running stop command.", ex);
        }
    }

    // inspects a container/image and returns json
    public String inspect(String name) {
        Preconditions.checkNotNull(name, "name must not be null!");
        ProcessBuilder pb = new ProcessBuilder(DOCKER_PROCESS, DOCKER_INSPECT, name);
        try {
            List<String> output = ProcessUtils.runProcess(pb);
            String collected = output.stream().collect(Collectors.joining(""));
            return DockerUtils.formatDockerJson(collected);
        } catch (IOException | InterruptedException ex) {
            return "";
        }
    }

    // does 'docker ps'
    public List<Container> ps(boolean all) {
        List<String> commands = new ArrayList<>();
        commands.add(DOCKER_PROCESS);
        commands.add(DOCKER_PS);
        if (all) {
            commands.add(DOCKER_ALL_MOD);
        }
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            List<String> output = ProcessUtils.runProcess(pb);
            return output.stream().skip(1).map(s -> {
                String parsedName = DockerUtils.parseContainerName(s);
                return new Container(inspect(parsedName));
            }).collect(Collectors.toList());
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }

    // gets version
    public Version getVersion() {
        ProcessBuilder pb = new ProcessBuilder(DOCKER_PROCESS, DOCKER_VERSION_MOD);
        try {
            List<String> output = ProcessUtils.runProcess(pb);
            String first = output.get(0);
            String[] split = first.split(" ");
            return new Version(split[2].replace(",", ""));
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }

    // checks if current docker binary is supported
    public boolean isValidVersion() {
        return MINIMUM_SUPPORTED_VERSION.compareTo(getVersion()) <= 0;
    }
}
