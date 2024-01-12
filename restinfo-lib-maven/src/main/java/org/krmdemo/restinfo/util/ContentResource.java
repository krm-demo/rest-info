package org.krmdemo.restinfo.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import static java.lang.String.format;

@Slf4j @Getter
public class ContentResource extends ModuleResource {

    @JsonIgnore
    protected String content;

    @Override
    protected void init(InputStream inputStream) throws IOException {
        this.content = IOUtils.toString(inputStream, Charset.defaultCharset());
    }

    /**
     * Load the manifest-descriptor of module for the passed class.
     *
     * @param clazz the class, whose module's manifest should be loaded
     * @return an optional value of {@link ManifestResource} (empty if not available)
     */
    public static Optional<ContentResource> loadContent(Class<?> clazz, String resourcePath) {
        return load(clazz, resourcePath, ContentResource::new);
    }

    @Override
    public String toString() {
        return format("%s:\n%s", super.toString(), getContent());
    }
}
