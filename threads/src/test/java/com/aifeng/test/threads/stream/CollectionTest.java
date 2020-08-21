package com.aifeng.test.threads.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/12 23:22
 */
public class CollectionTest {

    @Test
    public void testStream() {
        List<Integer> list = getList();

        // 串行流
        list.stream();
        list.stream().sequential();

        // 并行流
        list.stream().parallel();
        list.parallelStream().reduce(null);

        // 串行流执行
        list.stream().reduce((first, second) -> first+second);

        // 并行流执行
        list.parallelStream().reduce((first, second) -> first+second);
    }

    private List<Integer> getList() {
//        return Stream.of(new String[]{"ahead", "bench", "consider", "define", "fault", "group", "healthy", "idempotent"
//                , "kill", "love", "mind", "node", "optional", "party", "question", "reserve", "strong",
//                "tell", "under", "version", "wish", "x", "yellow", "zone"}).collect(Collectors.toList());
        return Stream.of(new Integer[]{123,234,412,512,674,723,892,912,1023,1183,1238,1348,1475}).collect(Collectors.toList());

    }

    public static void main(String[] args) {
        System.out.println(getBindType(null));
    }

    private static String getBindType(Integer bindType) {
        return 0 == bindType ? "INDIVIDUAL" : "ENTERPRISE";
    }
}
