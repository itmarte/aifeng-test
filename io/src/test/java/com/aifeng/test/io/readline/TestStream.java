package com.aifeng.test.io.readline;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/6/1 10:59
 */
public class TestStream {
    public static final String FILE_PATH = "C:\\Users\\dengxf\\Desktop\\necessary\\交接\\商户站\\shopee\\readTest\\10.csv";

    public static void main(String[] args) {
        try {
            AtomicInteger count = new AtomicInteger(0);
            Stream<String> stream = Files.lines(Paths.get(FILE_PATH), StandardCharsets.UTF_8);
            stream.forEach(dd -> {
                System.out.println(dd);
                if(count.getAndIncrement() > 10) {
                    throw new IllegalArgumentException("");
                }
            });
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
