package org.krmdemo.restinfo;

import org.krmdemo.restinfo.util.ManifestResource;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.Collections.*;
import static java.util.EnumSet.*;

public enum RestInfoKind {

    /**
     * Information about <b>S</b>ource-<b>C</b>ontrol-<b>M</b>anagement system,
     * where application sources were taken from for further build, push and run.
     * At the moment only data about local GIT-repository and remote Git-Hub and Bit-Bucket
     * are provided.
     */
    SCM_INFO,

    /**
     * Information about where, how and when the application sources were built.
     * The build could be performed locally (via IDE or command-line) or remotely,
     * using continues-integration abilities of Git-Hub, Bit-Bucket, Jenkins, Travis, etc.
     */
    BUILD_INFO,

    /**
     * Information about where and when the application was deployed and started.
     */
    START_UP_INFO;

    /**
     * This set represents all kind of information is requested
     */
    public static final Set<RestInfoKind> ALL = unmodifiableSet(allOf(RestInfoKind.class));

    /**
     * This set indicates that none of information is requested
     */
    public static final Set<RestInfoKind> NONE = unmodifiableSet(noneOf(RestInfoKind.class));

    /**
     * JVM entry-point to check the content of jar-manifest
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Optional<ManifestResource> mf = ManifestResource.restInfoManifest();
        if (mf.isEmpty()) {
            System.err.println("no manifest is available");
            System.exit(1);
        }
        System.out.println(mf);
    }
}
