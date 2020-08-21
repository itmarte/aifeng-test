package com.aifeng.test.io.readline;

import sun.misc.GC;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @Description: 分批处理文件
 * @author: imart·deng
 * @date: 2020/5/28 16:20
 */
public class TestReadSplit {
    public static final String FILE_PATH = "C:\\Users\\dengxf\\Desktop\\necessary\\交接\\商户站\\shopee\\readTest\\10.csv";
    public static void main(String[] args) throws IOException {
        final AtomicInteger count = new AtomicInteger(0);
        final Semaphore semaphore = new Semaphore(1000);
        long start = 0L;
        ExecutorService executorService = getExecutorService(1000, 40, 60);
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH), StandardCharsets.UTF_8)) {
            start = System.currentTimeMillis();
            List<Future<OriOrder>> batchResult = new ArrayList<>(1000);
            stream.forEach(str -> {
                final int lineNum = count.incrementAndGet();
//                try {
//                    semaphore.acquire();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                batchResult.add(executorService.submit(()->{
                    OriOrder order = new OriOrder();
                    order.setData(str);
                    order.setLineNumber(lineNum);
//                    System.out.println(str);

//                    semaphore.release();
                    return order;
                }));

                // 每批次1000，满了就收割
                if(batchResult.size() == 1000){
                    batchResult.forEach(fo -> {
                        try {
                            OriOrder order = fo.get();
                            System.out.println(order);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                    batchResult.clear();
                    System.out.println("-----------***********************************------------");
                    System.out.println("-----------*****千次批结束****------------");
                    System.out.println("-----------***********************************------------");

                    // 建议gc
//                    System.gc();
                }
            });

            if(batchResult.size() > 0){
                batchResult.forEach(fo -> {
                    try {
                        OriOrder order = fo.get();
                        System.out.println(order);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
                batchResult.clear();
                System.out.println("-----------***********************************------------");
                System.out.println("-----------*****千次批结束****------------");
                System.out.println("-----------***********************************------------");

                // 建议gc
//                    System.gc();
            }
        } finally {
            executorService.shutdown();
            try {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
                if(executorService.isTerminated()){
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                    System.out.println(String.format("[运行总耗时=%d]-----[总行数=%d]", System.currentTimeMillis() - start, count.get()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static ExecutorService getExecutorService(int queueSize, int processors, final int thresholdTimeWait) {

        return new ThreadPoolExecutor(processors, processors, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize),
                Executors.defaultThreadFactory(), (final Runnable r, final ThreadPoolExecutor executor) -> {
            int size = executor.getQueue().size();
            int remain = executor.getQueue().remainingCapacity();
            // 过载警告
            System.out.println(String.format("[线程池过载警告], 线程池过载，当前线程池任务队列大小[{%s}]", size));
            try {
                executor.getQueue().offer(r, thresholdTimeWait, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println(String.format("[线程池拒绝策略超时],超时原因[{%s}]", e.getMessage()));
                throw new RejectedExecutionException("Interrupted waiting for BrokerService.worker");
            }
        });
    }

    public static class OriOrder implements Serializable {
        private int lineNumber;
        private String data;
        private String reason;

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("OriOrder{");
            sb.append("lineNumber=").append(lineNumber);
            sb.append(", data='").append(data).append('\'');
            sb.append(", reason='").append(reason).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
