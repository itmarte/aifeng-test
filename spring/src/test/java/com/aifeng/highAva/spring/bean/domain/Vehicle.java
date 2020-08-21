package com.aifeng.highAva.spring.bean.domain;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 11:00
 */
public class Vehicle {
    private String name;
    private Integer wheel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWheel() {
        return wheel;
    }

    public void setWheel(Integer wheel) {
        this.wheel = wheel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vehicle{");
        sb.append("name='").append(name).append('\'');
        sb.append(", wheel=").append(wheel);
        sb.append('}');
        return sb.toString();
    }
}
