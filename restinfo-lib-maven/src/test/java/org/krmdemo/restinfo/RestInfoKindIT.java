package org.krmdemo.restinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.krmdemo.restinfo.util.ManifestResource;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.krmdemo.restinfo.util.StreamUtils.stream;

/**
 * Integration-test for {@link ManifestResource#restInfoManifest()}
 * <p/>
 * Must be executed via "verify" phase of maven lifecycle (or phases that later "package"),
 * otherwise (in "test" phase) some artifacts like "MANIFEST.MF" are not generated yet
 */
@Slf4j
public class RestInfoKindIT {

    @Test
    @Tag("integration-test-tag")
    void testManifest() throws IOException {
        Optional<ManifestResource> mf = ManifestResource.restInfoManifest();
        assertThat(mf).isNotEmpty();
        log.info("rest-info manifest as JSON:\n\n" + mf.get());
        log.info("the raw rest-info manifest is:\n\n" + mf.get().getContent());
        //log.info("rest-info manifest as YAML:\n\n" + mf.get().asYAML());
        // TODO: check values for 'git-hub'
    }
}
