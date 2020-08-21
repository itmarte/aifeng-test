package com.aifeng.test.threads.pool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TestFutureThrow {
    public static void main(String[] args) {
        Stream.of(new String[]{"a","b","c","d"}).forEach(str -> {
            if(Objects.equals("c", str)) return;
            System.out.println(str);
        });
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
    }

    public static void main2(String[] args) throws InterruptedException {
        ExecutorService executorService = Options.getExecutorService();
        executorService.shutdownNow();
        System.out.println("线程开始>>>>>>>>>>");
        List<Future<Integer>> futureList = new ArrayList<>(100);
        for (int i=0; i < 100; i++) {
            int tmp = i;
            executorService.submit((Callable<Integer>) () -> {
                if(tmp % 10 == 0){
                    throw new IllegalArgumentException("异常测试");
                }
                System.out.println(tmp);

                return tmp;
            });
        }



        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        System.out.println("线程结束>>>>>>>>>>");

    }
}
