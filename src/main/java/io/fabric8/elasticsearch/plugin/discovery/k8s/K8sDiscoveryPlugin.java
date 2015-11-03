package io.fabric8.elasticsearch.plugin.discovery.k8s;

import io.fabric8.elasticsearch.cloud.k8s.K8sModule;
import io.fabric8.elasticsearch.cloud.k8s.K8sServiceImpl;
import io.fabric8.elasticsearch.discovery.k8s.K8sDiscovery;
import io.fabric8.elasticsearch.discovery.k8s.K8sUnicastHostsProvider;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.DiscoveryModule;
import org.elasticsearch.plugins.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class K8sDiscoveryPlugin extends Plugin {

    private final Settings settings;
    protected final ESLogger logger = Loggers.getLogger(K8sDiscoveryPlugin.class);

    public K8sDiscoveryPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String name() {
        return "cloud-kubernetes";
    }

    @Override
    public String description() {
        return "Cloud Kubernetes Plugin";
    }

    @Override
    public Collection<Module> nodeModules() {
        Collection<Module> modules = new ArrayList<>();
        modules.add(new K8sModule());
        return modules;
    }

    @Override
    public Collection<Class<? extends LifecycleComponent>> nodeServices() {
        Collection<Class<? extends LifecycleComponent>> services = new ArrayList<>();
        services.add(K8sServiceImpl.class);
        return services;
    }

    public void onModule(DiscoveryModule discoveryModule) {
        if (K8sModule.isK8sDiscoveryActive(settings, logger)) {
            discoveryModule.addDiscoveryType("ec2", K8sDiscovery.class);
            discoveryModule.addUnicastHostProvider(K8sUnicastHostsProvider.class);
        }
    }

}
