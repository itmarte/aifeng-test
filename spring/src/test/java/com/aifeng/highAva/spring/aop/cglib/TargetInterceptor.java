package com.aifeng.highAva.spring.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 16:50
 */
public class TargetInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("调用前");
//        Object result = methodProxy.invokeSuper(o, objects);
//        System.out.println(" 调用后"+result);
//        return result;
        try {
            System.out.println(String.format("[%s] Proxy before ...", method.getName()));
            Object obj = methodProxy.invokeSuper(o, objects);
            System.out.println(String.format("[%s] Proxy after ...", method.getName()));
            return obj;
        } catch (Throwable throwable) {
            System.out.println(String.format("[%s]Proxy exception ...", method.getName()));
            throwable.printStackTrace();
        } finally {
            System.out.println(String.format("[%s] Proxy finally ...", method.getName()));
        }
        return null;
    }
}
