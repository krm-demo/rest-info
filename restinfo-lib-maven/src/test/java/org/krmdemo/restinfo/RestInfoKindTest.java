package org.krmdemo.restinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.krmdemo.restinfo.util.ManifestResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.krmdemo.restinfo.util.StreamUtils.stream;

/**
 * Unit-test for {@link RestInfoKind} and {@link ManifestResource#restInfoManifest()}
 */
@Slf4j
public class RestInfoKindTest {

    @Test
    void testAll() {
        assertThat(RestInfoKind.ALL).containsExactly(
            RestInfoKind.SCM_INFO,
            RestInfoKind.BUILD_INFO,
            RestInfoKind.START_UP_INFO
        );
    }

    @Test
    void testNone() {
        assertThat(RestInfoKind.NONE).isEmpty();
    }

    @Test
    void testNoManifest() throws IOException {
        assertThat(ManifestResource.restInfoManifest()).isEmpty();
        if (log.isDebugEnabled()) {
            log.debug("all resources from class-loader:");
            String clr = ManifestResource.MANIFEST_RES_PATH.substring(1);
            stream(getClass().getClassLoader().getResources(clr)).forEach(url -> log.debug(" ::: " + url));
        }
    }
}
