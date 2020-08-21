package com.aifeng.highAva.spring;

import com.aifeng.highAva.spring.bean.Service.UserService;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 10:40
 */
public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        context.registerShutdownHook();
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getPersonal().sayHello());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (int i=0; i < 10; i++) {
                System.out.println(i);
            }
        }));
    }
}
