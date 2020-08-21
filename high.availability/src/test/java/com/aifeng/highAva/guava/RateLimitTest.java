package com.aifeng.highAva.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/7 17:41
 */
public class RateLimitTest {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        String start = dateFormat.format(new Date());
        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个
        for (int i = 1; i <= 10; i++) {
            double waitTime = limiter.acquire(i);// 请求RateLimiter, 超过permits会被阻塞
            System.out.println("cutTime=" + System.currentTimeMillis() + " call execute:" + i + " waitTime:" + waitTime);
        }
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("start time:" + start);
        System.out.println("end time:" + end);
    }
}
