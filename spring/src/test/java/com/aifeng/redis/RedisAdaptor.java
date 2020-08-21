package com.aifeng.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: redis 适配器
 * @author: imart·deng
 * @date: 2020/4/20 17:32
 */
public class RedisAdaptor {
    RedisTemplate<String, Object> redisTemplate;

    public void test() {
//        redisTemplate.opsForValue().setIfAbsent();
    }
}
