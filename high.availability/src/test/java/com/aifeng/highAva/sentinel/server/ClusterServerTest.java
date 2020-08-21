package com.aifeng.highAva.sentinel.server;

import com.aifeng.highAva.sentinel.common.DemoConstants;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.init.InitFunc;

import java.util.Collections;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/8/7 17:45
 */
public class ClusterServerTest {

    public static void main(String[] args) throws Exception {
        // 服务端配置
        initServer();

        // 创建服务
        startSever();
    }

    private static void startSever() throws Exception {
        // Not embedded mode by default (alone mode).
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();

        // A sample for manually load config for cluster server.
        // It's recommended to use dynamic data source to cluster manage config and rules.
        // See the sample in DemoClusterServerInitFunc for detail.
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600)
                .setPort(DemoConstants.PORT));
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(DemoConstants.APP_NAME));

        // Start the server.
        tokenServer.start();


        System.out.println(ClusterFlowRuleManager.getAllFlowRules());
    }

    private static void initServer() throws Exception {
        InitFunc initFunc = new ClusterServerInitFunc();
        initFunc.init();
    }
}
