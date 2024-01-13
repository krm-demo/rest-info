package org.krmdemo.restinfo.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import org.snakeyaml.engine.v2.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.jar.*;

import static java.lang.String.format;
import static org.krmdemo.restinfo.util.StreamUtils.propsMap;
import static org.krmdemo.restinfo.util.StreamUtils.toSortedMap;

@Slf4j @Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManifestResource extends ContentResource {

    /**
     * Standard path to manifest-descriptor inside jar-file
     */
    public static final String MANIFEST_RES_PATH = "/META-INF/MANIFEST.MF";

    /**
     * Standard manifest-descriptor
     */
    @JsonIgnore
    protected Manifest manifest;

    /**
     * Manifest entries in main section
     */
    protected SortedMap<String, String> mainSection;

    /**
     * Secondary sections of manifest
     */
    protected SortedMap<String, SortedMap<String, String>> sections;

    @Override
    protected void init(InputStream inputStream) throws IOException {
        super.init(inputStream);
        this.manifest = new Manifest(IOUtils.toInputStream(this.content, Charset.defaultCharset()));
        this.mainSection = propsMap(this.manifest.getMainAttributes());
        this.sections = this.manifest.getEntries().entrySet().stream().collect(
            toSortedMap(StreamUtils::propertyKey, e -> propsMap(e.getValue())));
    }

    /**
     * Load the manifest-descriptor of module for the passed class.
     *
     * @param clazz the class, whose module's manifest should be loaded
     * @return an optional value of {@link ManifestResource} (empty if not available)
     */
    public static Optional<ManifestResource> loadManifest(Class<?> clazz) {
        return load(clazz, MANIFEST_RES_PATH, ManifestResource::new);
    }

    /**
     * Load the manifest-descriptor of this (rest-info) module.
     *
     * @return an optional value of {@link ManifestResource} (empty if not available)
     */
    public static Optional<ManifestResource> restInfoManifest() {
        return loadManifest(ManifestResource.class);
    }

    public String asPrettyJson() {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception ex) {
            log.atError().log("error dumpins as JSON", ex);
            return "<< " + ex.getMessage() + ">> " + this;
        }
    }

    public String asYAML() {
        Dump dump = new Dump(DumpSettings.builder()
            .setExplicitEnd(true)
            .setDumpComments(true)
            .setCanonical(true)
            .build());
        return dump.dumpToString(this);
    }

    @Override
    public String toString() {
        return format("Manifest(%s) - '%s':\n%s", getModuleName(), getResourceURL(), asPrettyJson());
    }
}
