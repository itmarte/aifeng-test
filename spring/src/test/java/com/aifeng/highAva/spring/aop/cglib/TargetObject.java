package com.aifeng.highAva.spring.aop.cglib;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 16:48
 */
public class TargetObject {

    public String method1(String paramName) {
        return paramName;
    }

    public int method2(int count) {
        System.out.println(this.method1("method2 test"));
        return count;
    }

    public int method3(int count) {
        method4();
        return count;
    }

    private void method4() {
        System.out.println("method4.................");
    }

    @Override
    public String toString() {
        return "TargetObject []" + getClass();
    }
}
