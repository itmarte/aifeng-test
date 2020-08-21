package com.aifeng.highAva.spring.bean.domain;

import com.aifeng.highAva.spring.aop.annotation.LogCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 10:40
 */
@Component
public class Personal {
    private String name;
    private Integer age;
    @Autowired
    private Bird bird;
    @Autowired
    private Fish fish;
    @Resource(name = "car")
    private Vehicle vehicle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @LogCut
    public String sayHello() {
        final StringBuilder sb = new StringBuilder("Personal{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", bird=").append(bird.sayHello());
        sb.append(", fish=").append(fish.sayHello());
        sb.append(", vehicle=").append(vehicle);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Personal{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", bird=").append(bird);
        sb.append(", fish=").append(fish);
        sb.append(", vehicle=").append(vehicle);
        sb.append('}');
        return sb.toString();
    }
}
