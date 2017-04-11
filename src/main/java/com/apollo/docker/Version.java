package com.apollo.docker;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

// For comparing versions
public class Version implements Comparable<Version> {
    public final int major, minor, patch;
    private static final String VERSION_ERROR = "Version string must be in proper a proper semantic version.";

    // Parses a semantic version
    public Version(String parse) {
        Preconditions.checkNotNull(parse, "Parse must not be null!");
        String[] spl = parse.split("\\.");
        if (spl.length != 3) {
            throw new RuntimeException(VERSION_ERROR);
        }
        try {
            major = Integer.parseInt(spl[0]);
            minor = Integer.parseInt(spl[1]);
            patch = Integer.parseInt(spl[2]);
        } catch (NumberFormatException ex) {
            throw new RuntimeException(VERSION_ERROR, ex);
        }
    }

    // Creates a version out of the 3 semantic numbers
    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @Override
    public int compareTo(Version that) {
        return new CompareToBuilder()
            .append(this.major, that.major)
            .append(this.minor, that.minor)
            .append(this.patch, that.patch)
            .toComparison();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Version)) {
            return false;
        }
        Version that = (Version) other;
        return new EqualsBuilder()
            .append(this.major, that.major)
            .append(this.minor, that.minor)
            .append(this.patch, that.patch)
            .isEquals();
    }

    @Override
    public String toString() {
        return "(" + major + "." + minor + "." + patch + ")";
    }
}
