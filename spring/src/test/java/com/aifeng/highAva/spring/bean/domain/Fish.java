package com.aifeng.highAva.spring.bean.domain;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 10:57
 */
@Component
public class Fish extends Animal {
    public static final String HELLO_WORLD = "Hello, i can swim";
    @Override
    public String sayHello() {
        return HELLO_WORLD;
    }
}
