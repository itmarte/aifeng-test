package com.aifeng.highAva.spring.aop.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 17:21
 */
public class TargetFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if(method.getName().equals("method1")){
            System.out.println("filter method1 ==0");
            return 0;
        }
        if(method.getName().equals("method2")){
            System.out.println("filter method2 ==1");
            return 1;
        }
        if(method.getName().equals("method3")){
            System.out.println("filter method3 ==2");
            return 1;
        }
        return 0;
    }
}
