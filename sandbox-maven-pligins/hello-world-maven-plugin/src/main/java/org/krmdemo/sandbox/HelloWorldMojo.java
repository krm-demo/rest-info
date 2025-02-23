package org.krmdemo.sandbox;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MavenPluginManager;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.codehaus.plexus.component.annotations.Requirement;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * A simplest MOJO that says "Hello!" to {@link #helloTo} in maven-log.
 */
@Mojo(name = "say-hello", requiresProject = false, defaultPhase = LifecyclePhase.NONE)
public class HelloWorldMojo extends AbstractMojo {

    /**
     * A name to say "Hello!" to
     */
    @Parameter(property = "hello-to", defaultValue="World")
    String helloTo;

    /**
     * Gives access to the Maven project information.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    private MavenSession session;

    /**
     * This parameter can't be configured in the {@code pom.xml}.
     * It represents the Maven Session and will be injected by maven itself.
     */
    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    public void setSession(MavenSession session) {
        this.session = session;
    }

    @Override
    public void execute() {
        getLog().info(String.format("Hello, %s!", helloTo));
        if (!session.getRequest().isProjectPresent()) {
            getLog().warn("not inside the maven-project");
        } else {
            getLog().info(String.format("inside the maven-project '%s'", project.getName()));
        }
        System.out.println("session.systemProps:\n" + dumpProps(session.getSystemProperties()));
        System.out.println("session.userProps:\n" + dumpProps(session.getUserProperties()));
    }

    private String dumpProps(Properties props) {
        return dumpMap(toSortedMap(props));
    }

    private String dumpMap(Map<?,?> mapToDump) {
        return mapToDump.entrySet().stream()
            .map(e -> String.format("- %s: \"%s\"", e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n",
                String.format("## ---- %3d items: ---- ##\n", mapToDump.size()),
                            "\n## ---------------------##"));
    }

    private SortedMap<String, String> toSortedMap(Properties props) {
        return props.entrySet().stream().collect(toMap(
            this::propName, this::propValueEscaped, (x, y) -> y, TreeMap::new));
    }

    private String propName(Map.Entry<Object,Object> propEntry) {
        return "" + propEntry.getKey();
    }

    private String propValueEscaped(Map.Entry<Object,Object> propEntry) {
        return StringEscapeUtils.escapeJava("" + propEntry.getValue());
    }
}
