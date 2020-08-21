package com.aifeng.test.threads.account.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {
    private static final long TIME_OFFSET = LocalDateTime.of(2020, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC) * 1000;
    private static final int RANDOM_BUCKET_VALUE = (new Random().nextInt(65536) ^ new Random().nextInt(65536)) << 8;
    private static final int LOW_ORDER_BYTES = Double.valueOf(Math.pow(2, 8)).intValue() - 1;
    private static final AtomicLong COUNTER = new AtomicLong(new Random().nextInt(1000000));

    public static long get() {
        return get(RANDOM_BUCKET_VALUE, COUNTER.incrementAndGet());
    }

    /**
     * 使用业务定义的分桶值生成id
     *
     * @param bucketVal 分桶值
     * @return id
     */
    public static long get(long bucketVal) {
        return get(bucketVal, COUNTER.incrementAndGet());
    }

    private static long get(long bucketVal, long index) {
        return ((System.currentTimeMillis() - TIME_OFFSET) << 22) | bucketVal | (index & LOW_ORDER_BYTES);
    }
}