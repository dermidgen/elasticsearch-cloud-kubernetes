package io.fabric8.elasticsearch.discovery.k8s;

import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.cluster.ClusterService;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.DiscoverySettings;
import org.elasticsearch.discovery.zen.ZenDiscovery;
import org.elasticsearch.discovery.zen.elect.ElectMasterService;
import org.elasticsearch.discovery.zen.ping.ZenPingService;
import org.elasticsearch.node.settings.NodeSettingsService;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

/**
 *
 */
public class K8sDiscovery extends ZenDiscovery {

  public static final String K8s = "kubernetes";

  @Inject
  public K8sDiscovery(Settings settings, ClusterName clusterName, ThreadPool threadPool, TransportService transportService,
                      ClusterService clusterService, NodeSettingsService nodeSettingsService, ZenPingService pingService,
                      DiscoverySettings discoverySettings,
                      ElectMasterService electMasterService) {
    super(settings, clusterName, threadPool, transportService, clusterService, nodeSettingsService,
            pingService, electMasterService, discoverySettings);
  }

}
