package org.krmdemo.restinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.krmdemo.restinfo.util.ManifestResource;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.krmdemo.restinfo.util.StreamUtils.stream;

/**
 * Integration-test for {@link ManifestResource#restInfoManifest()}
 */
@Slf4j
public class RestInfoKindIT {

    @Test
    void testNoManifest() throws IOException {
        Optional<ManifestResource> mf = ManifestResource.restInfoManifest();
        assertThat(mf).isNotEmpty();
        log.info("ManifestResource.restInfoManifest() returns:\n\n" + mf.get());
        log.info("the raw of rest-info manifest is:\n\n" + mf.get().getContent());
        // TODO: check values for 'git-hub'
    }
}
