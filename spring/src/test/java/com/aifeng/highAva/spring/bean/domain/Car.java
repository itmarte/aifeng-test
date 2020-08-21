package com.aifeng.highAva.spring.bean.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 11:09
 */
@Component
public class Car extends Vehicle implements InitializingBean, DisposableBean {
    private String name = "car";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("after properties set...... i will .");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("before destroy..... i will ...");
    }
}
