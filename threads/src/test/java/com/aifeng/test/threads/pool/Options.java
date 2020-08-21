package com.aifeng.test.threads.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/26 13:54
 */
public class Options {
    public static final Logger logger = LoggerFactory.getLogger(Options.class);

    public static final int DEFAULT_QUEUE_SIZE = 1000;

    public static final int THRESHOLD = 100;

    public static final int QUEUE_TIME_WAIT = 60;

    /**
     * 通用executor，用于做异步处理
     */
    private static ExecutorService commonExecutor;

    private static Object mutex = new Object();


    public static ExecutorService getExecutorService() {
        return getExecutorService(DEFAULT_QUEUE_SIZE, Runtime.getRuntime().availableProcessors(), QUEUE_TIME_WAIT);
    }

    public static ExecutorService getSingleThreadExecutorService() {
        return getExecutorService(DEFAULT_QUEUE_SIZE, 1, QUEUE_TIME_WAIT);
    }

    public static ExecutorService getExecutorService(int queueSize) {
        return getExecutorService(queueSize, Runtime.getRuntime().availableProcessors(), QUEUE_TIME_WAIT);
    }

    public static ExecutorService getExecutorService(int queueSize, int processors, final int thresholdTimeWait) {

        return new ThreadPoolExecutor(processors, processors, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize),
                Executors.defaultThreadFactory(), (final Runnable r, final ThreadPoolExecutor executor) -> {
            int size = executor.getQueue().size();
            int remain = executor.getQueue().remainingCapacity();
            // 过载警告
            logger.warn("[线程池过载警告], 线程池过载，当前线程池任务队列大小[{}]", size);
            try {
                executor.getQueue().offer(r, thresholdTimeWait, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("[线程池拒绝策略超时],超时原因[{}]", e.getMessage(), e);
                throw new RejectedExecutionException("Interrupted waiting for BrokerService.worker");
            }
        });
    }

    /**
     * 创建单例通用executor
     * @return
     */
    public static ExecutorService getCommonExecutor() {
        if(null != commonExecutor) return commonExecutor;
        synchronized (mutex){
            if(null != commonExecutor) return commonExecutor;
            commonExecutor = getExecutorService();
            return commonExecutor;
        }
    }

}
