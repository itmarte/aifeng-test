package com.aifeng.highAva.sentinel.server;

import com.aifeng.highAva.sentinel.common.DemoConstants;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/8/9 16:58
 */
public class ClusterServerInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        // Token server transport config extracted from assign map:
        initServerTransportConfigProperty();

        initNameSpace();

        // Init cluster state property for extracting mode from cluster map data source.
        initStateProperty();

        registerClusterRuleSupplier();
    }

    private static void initNameSpace() {
        ClusterServerConfigManager.loadServerNamespaceSet(Stream.of(new String[]{DemoConstants.APP_NAME}).collect(Collectors.toSet()));
    }

    private static void initStateProperty() {
    }

    private static void initServerTransportConfigProperty() {
        ServerTransportConfig config = new ServerTransportConfig();
        config.setIdleSeconds(300);
        config.setPort(DemoConstants.PORT);
        ClusterServerConfigManager.loadGlobalTransportConfig(config);
    }

    private static void registerClusterRuleSupplier() {
        FlowRule flowRule = new FlowRule();
        flowRule.setCount(100);
        flowRule.setResource("1231");
        flowRule.setClusterMode(true);
        flowRule.setRefResource("ref");

        // 集群配置
        ClusterFlowConfig clusterFlowConfig = new ClusterFlowConfig();
        clusterFlowConfig.setFallbackToLocalWhenFail(true);
        clusterFlowConfig.setThresholdType(1);
        clusterFlowConfig.setFlowId(1231L);
        flowRule.setClusterConfig(clusterFlowConfig);

        ClusterFlowRuleManager.loadRules(DemoConstants.APP_NAME, Arrays.asList(flowRule));
    }
}
