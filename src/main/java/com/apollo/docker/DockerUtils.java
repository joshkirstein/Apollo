package com.apollo.docker;

import com.google.common.base.Preconditions;

public class DockerUtils {
    // Cuts of the leading [ and trailing ] so we can parse the JSON correctly
    static String formatDockerJson(String jsonString) {
        Preconditions.checkNotNull(jsonString, "jsonString must not be null!");
        jsonString = jsonString.trim();
        if (!jsonString.startsWith("[")) {
            return jsonString;
        }
        return jsonString.substring(1).substring(0, jsonString.length() - 2);
    }

    // Gets the name from a docker container string
    static String parseContainerName(String dockerString) {
        Preconditions.checkNotNull(dockerString, "dockerString must not be null!");
        String[] spl = dockerString.split(" ");
        return spl[spl.length - 1];
    }

    // Gets the image hash from the output string
    static String parseImageHash(String dockerString) {
        Preconditions.checkNotNull(dockerString, "dockerString must not be null!");
        String[] spl = dockerString.split(":");
        return spl[2];
    }
}
