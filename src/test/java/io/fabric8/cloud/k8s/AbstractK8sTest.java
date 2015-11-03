package io.fabric8.cloud.k8s;

import com.carrotsearch.randomizedtesting.annotations.TestGroup;
import io.fabric8.elasticsearch.plugin.discovery.k8s.K8sDiscoveryPlugin;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsException;
import org.elasticsearch.env.Environment;
import org.elasticsearch.test.ESIntegTestCase;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
public abstract class AbstractK8sTest extends ESIntegTestCase {

    /**
     * Annotation for tests that require GCE to run. GCE tests are disabled by default.
     * See README file for details.
     */
    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @TestGroup(enabled = true, sysProperty = SYSPROP_K8S)
    public @interface K8sTest {
    }

    /**
     */
    public static final String SYSPROP_K8S = "tests.k8s";

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        Settings.Builder settings = Settings.builder()
                .put(super.nodeSettings(nodeOrdinal))
                .put("plugin.types", K8sDiscoveryPlugin.class.getName());


        Environment environment = new Environment(settings.build());

        // if explicit, just load it and don't load from env
        try {
            if (Strings.hasText(System.getProperty("tests.config"))) {
                settings.loadFromPath(environment.resolveRepoFile(System.getProperty("tests.config")));
            } else {
                fail("to run integration tests, you need to set -Dtest.k8s=true and -Dtests.config=/path/to/elasticsearch.yml");
            }
        } catch (SettingsException exception) {
            fail("your test configuration file is incorrect: " + System.getProperty("tests.config"));
        }
        return settings.build();
    }

}
