package io.fabric8.elasticsearch.cloud.k8s;

import io.fabric8.elasticsearch.discovery.k8s.K8sDiscovery;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;

public class K8sModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(K8sService.class).to(K8sServiceImpl.class).asEagerSingleton();
    }

    /**
     * Check if discovery is meant to start
     * @return true if we can start discovery features
     */
    public static boolean isK8sDiscoveryActive(Settings settings, ESLogger logger) {
        // User set discovery.type: ec2
        if (!K8sDiscovery.K8s.equalsIgnoreCase(settings.get("discovery.type"))) {
            logger.trace("discovery.type not set to {}", K8sDiscovery.K8s);
            return false;
        }

        return true;
    }
}
