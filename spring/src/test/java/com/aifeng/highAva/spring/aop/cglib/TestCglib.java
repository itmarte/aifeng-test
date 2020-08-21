package com.aifeng.highAva.spring.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 16:54
 */
public class TestCglib {
    public static void main(String[] args) {
        test1();

//        test2();
    }

    public static void test1() {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject2=(TargetObject)enhancer.create();
        System.out.println("test toString ======>");
        System.out.println(targetObject2);
        System.out.println("test method1 ======>");
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println("test method2 ======>");
        System.out.println(targetObject2.method2(100));
        System.out.println("test method3 ======>");
        System.out.println(targetObject2.method3(200));
    }

    public static void test2() {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        MethodInterceptor[] arr = new MethodInterceptor[]{new TargetInterceptor(), new TargetInterceptor2()};
        enhancer.setCallbacks(arr);
        enhancer.setCallbackFilter(new TargetFilter());
//        enhancer.setf
        TargetObject targetObject2=(TargetObject)enhancer.create();
        System.out.println("test toString ======>");
        System.out.println(targetObject2);
        System.out.println("test method1 ======>");
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println("test method2 ======>");
        System.out.println(targetObject2.method2(100));
        System.out.println("test method3 ======>");
        System.out.println(targetObject2.method3(200));
    }

}
