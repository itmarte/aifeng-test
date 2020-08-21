package com.aifeng.highAva.spring.bean.domain;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 10:40
 */
public abstract class Animal {
    private String name;
    private String weight;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType(){
        return this.getClass().getSimpleName();
    }

    public abstract String sayHello();
}
