package org.krmdemo.restinfo.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.function.*;

import static java.lang.String.format;

/**
 * The base-class of static resource inside java-module (since Java-9)
 */
@Slf4j @Getter
public abstract class ModuleResource {

    /**
     * The name of java-module (since Java-9)
     */
    protected String moduleName;

    /**
     * The relative path to resource inside the java-module
     */
    protected String resourcePath;

    /**
     * URL, which also contains the information about outer JAR-file of resource
     */
    protected URL resourceURL;

    /**
     * @param inputStream content of resource as input-stream
     * @throws IOException in case of I/O error
     */
    protected abstract void init(InputStream inputStream) throws IOException;

    /**
     * Load the resource of module for the passed class.
     *
     * @param clazz the class, whose module's resource should be loaded
     * @param resourcePath the relative path to resource inside the module of a class
     * @param factory a factory to create the descendant of {@link ModuleResource}
     * @return an optional with descendant of {@link ManifestResource} (empty if not available)
     * @param <MR> base class of module's resources
     */
    protected static <MR extends ModuleResource> Optional<MR> load(Class<?> clazz, String resourcePath, Supplier<MR> factory) {
        try (InputStream inputStream = clazz.getModule().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return Optional.empty();
            }
            MR moduleResource = factory.get();
            moduleResource.moduleName = clazz.getModule().getName();
            moduleResource.resourcePath = resourcePath;
            moduleResource.resourceURL = clazz.getResource(resourcePath);
            moduleResource.init(inputStream);
            return Optional.of(moduleResource);
        } catch (Exception ex) {
            log.atError().setCause(ex)
                .log("error while loading the resource '{}' for class {}", resourcePath, clazz);
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return format("%s('%s') - '%s'", getModuleName(), getResourcePath(), getResourceURL());
    }
}
