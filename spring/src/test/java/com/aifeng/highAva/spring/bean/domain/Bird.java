package com.aifeng.highAva.spring.bean.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 10:52
 */
@Component
@Scope("prototype")
public class Bird extends Animal {
    public static final String HELLO_WORLD = "Hello, i can fly.";

    @Override
    public String sayHello() {
        return HELLO_WORLD + this;
    }
}
