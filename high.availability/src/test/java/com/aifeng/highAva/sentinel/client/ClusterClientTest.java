package com.aifeng.highAva.sentinel.client;

import com.aifeng.highAva.sentinel.common.DemoConstants;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.TokenResult;
import com.alibaba.csp.sentinel.cluster.TokenResultStatus;
import com.alibaba.csp.sentinel.cluster.TokenService;
import com.alibaba.csp.sentinel.cluster.client.DefaultClusterTokenClient;
import com.alibaba.csp.sentinel.cluster.client.TokenClientProvider;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/8/7 17:45
 */
public class ClusterClientTest {

    public static void main(String[] args) throws Exception {
        System.setProperty("csp.sentinel.dashboard.server", "192.168.132.138:8080");
        System.setProperty("project.name", "cb-openapi-sentinel");

        // 注册客户端信息
        initClient();
        ClusterStateManager.applyState(0);
        TokenService tokenService = TokenClientProvider.getClient();
        long developerId = 1218L;
        long resourceId = 201912162745007L;
        long flowId = 6250005274921272514L;

        while (true){
            long start = System.currentTimeMillis();
            TokenResult result = tokenService.requestToken(6442452178L, 1, false);
//            TokenResult result = tokenService.requestToken(toFlowId(developerId, resourceId), 1, false);
            System.out.println(String.format("请求耗时[%s]", System.currentTimeMillis()- start));
            System.out.println(result);
            Thread.sleep(20);
        }


//        // 扫描所有规则
//        scanAllRules(tokenService);

//        // 手动获取token
//        TokenService tokenService = new DefaultClusterTokenClient();
////        ((DefaultClusterTokenClient)tokenService).start();
//        TokenResult result = tokenService.requestToken(flowId, 1, false);
//        System.out.println(result);
//        result = tokenService.requestToken(flowId, 10, false);
//        System.out.println(result);
//        result = tokenService.requestToken(flowId, 100, false);
//        System.out.println(result);
//        result = tokenService.requestToken(flowId, 1000, false);
//        System.out.println(result);
//
////        TokenResultStatus.OK;
//

//        // 框架获取token
//        while (true) {
//            Entry entry = null;
//            try {
//                entry = SphU.entry("1231");
//                /*您的业务逻辑 - 开始*/
//                System.out.println("hello world");
//                /*您的业务逻辑 - 结束*/
//            } catch (BlockException e1) {
//                /*流控逻辑处理 - 开始*/
//                System.out.println("block!");
//                /*流控逻辑处理 - 结束*/
//            } finally {
//                if (entry != null) {
//                    entry.exit();
//                }
//            }
//        }
    }

    public static final long MAX_LONG = (1L << 63)-1;
    private static long toFlowId(long developerId, long resourceId) {
        return ((developerId << 32)  + resourceId) & MAX_LONG;
    }

    private static void scanAllRules(TokenService tokenService) throws IOException, InterruptedException {
        List<String> lines = Files.readAllLines(Paths.get("D:\\flow.conf"));
        String[] configs = lines.get(0).replaceAll("},", "}#").split("#");
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final CountDownLatch countDownLatch = new CountDownLatch(configs.length - 1);
        final Random random = new Random(100);

        for (String config : configs) {
            JSONObject jsonObject = (JSONObject) JSON.parse(config);
            String f = String.valueOf(jsonObject.get("f"));
            executorService.submit(() -> {
                countDownLatch.countDown();
                for (int i = 0; i < 100; i++) {
                    int count = random.nextInt(10);
                    count = count == 0 ? 1 : count;
                    TokenResult result = tokenService.requestToken(Long.valueOf(f), count, false);
                    System.out.println(String.format("[%s]获取token数[%s], 返回结果[%s]", f, count, result));
                }
            });
        }

        countDownLatch.await();
        System.out.println("...............finished.........");
        System.out.println("...............finished.........");
        System.out.println("...............finished.........");
        System.out.println("...............finished.........");
        System.out.println("...............finished.........");
        System.out.println("...............finished.........");

        executorService.shutdown();
    }

    private static void initClient() {
        initDynamicRuleProperty();

        // Register token client related data source.
        // Token client common config:
        initClientConfigProperty();
        // Token client assign config (e.g. target token server) retrieved from assign map:
        initClientServerAssignProperty();
    }

    private static void initClientServerAssignProperty() {
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
//        assignConfig.setServerHost("192.168.132.138");
        assignConfig.setServerHost("127.0.0.1");
        assignConfig.setServerPort(DemoConstants.PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);
    }

    private static void initClientConfigProperty() {
        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(6000);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    private static void initDynamicRuleProperty() {
        FlowRule flowRule = new FlowRule();
        flowRule.setCount(10);
        flowRule.setResource("1231");
        flowRule.setClusterMode(true);
        flowRule.setRefResource("ref");

        // 集群配置
        ClusterFlowConfig clusterFlowConfig = new ClusterFlowConfig();
        clusterFlowConfig.setFallbackToLocalWhenFail(true);
        clusterFlowConfig.setThresholdType(1);
        clusterFlowConfig.setFlowId(6250005274921272514L);
        flowRule.setClusterConfig(clusterFlowConfig);

        FlowRuleManager.loadRules(Arrays.asList(flowRule));
    }
}
