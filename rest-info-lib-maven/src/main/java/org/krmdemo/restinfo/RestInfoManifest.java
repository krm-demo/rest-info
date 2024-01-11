package org.krmdemo.restinfo;

import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.*;

public class RestInfoManifest {

    static final String MANIFEST_RES_PATH = "/META-INF/MANIFEST.MF";

    URL url;
    String content;

    String manifestVersion;
    String createdBy;
    String buildJdkSpec;

    String buildTool;
    String buildJdk;
    String implementationTitle;
    String implementationVersion;
    String implementationPackage;


//    GITHUB_SERVER_URL --> 'https://github.com'
//    GITHUB_REPOSITORY --> 'krm-demo/docker-pipelines'
//    GITHUB_REPOSITORY_OWNER --> 'krm-demo'
//    GITHUB_REF --> 'refs/heads/main'
//    GITHUB_REF_NAME --> 'main'
//    GITHUB_REF_TYPE --> 'branch'
//    GITHUB_HEAD_REF --> ''
//    GITHUB_SHA --> '8f94bf48cfcffca4b6c8107c558dfe11b453c23a'
//
//    GITHUB_ACTOR --> 'krm-demo'
//    GITHUB_TRIGGERING_ACTOR --> 'krm-demo'
//    GITHUB_REPOSITORY_OWNER_ID --> '149853618'
//
//    GITHUB_WORKFLOW --> 'Manual Build & Push'
//    GITHUB_WORKFLOW_REF --> 'krm-demo/docker-pipelines/.github/workflows/manual.yml@refs/heads/main'
//    GITHUB_WORKFLOW_SHA --> '8f94bf48cfcffca4b6c8107c558dfe11b453c23a'
//    GITHUB_EVENT_NAME --> 'workflow_dispatch'
//    GITHUB_JOB --> 'hello-java'
//    GITHUB_RUN_ATTEMPT --> '1'
//    GITHUB_RUN_ID --> '7444164144'
//    Build-Url=https://github.com/krm-demo/docker-pipelines/actions/runs/7444164144
//    GITHUB_RUN_NUMBER --> '36'


//    Build-Jdk: 21.0.1 (Homebrew)
//    Build-Os: Mac OS X (13.2.1; aarch64)
//    Implementation-Title: rest-info-lib-maven
//    Implementation-Version: 1.0
//    Package: org.krmdemo.restinfo
//    Implementation-Build-Date: some-date
//    url:

    public static Optional<RestInfoManifest> getInstance() throws IOException {
        Class<?> clazz = RestInfoManifest.class;
        URL url = clazz.getResource(MANIFEST_RES_PATH);
        if (url == null) {
            return Optional.empty();
        }
        RestInfoManifest instance = new RestInfoManifest();
        Manifest mf = new Manifest(url.openStream());
        mf.getMainAttributes();
//        Arrays.stream(clazz.getDeclaredFields())
//            .filter()
//        if (instance.url != null) {
//            instance.content = instance.url.getContent();
//        }
        return Optional.of(instance);
    }

    private RestInfoManifest() {
    }

    /**
     * JVM entr-point to check the content of jar-manifest
     * @param args command-line arguments
     * @throws IOException in cases of I/O errors
     */
    public static void main(String[] args) throws IOException {
        URL manifestURL = RestInfoManifest.class.getResource(MANIFEST_RES_PATH);
        if (manifestURL == null) {
            System.err.printf("no manifest as resource '%s'%n", MANIFEST_RES_PATH);
            System.exit(1);
        }
        System.out.println(manifestURL.getContent());
    }
}
