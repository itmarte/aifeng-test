package com.aifeng.highAva.spring.bean;

import com.aifeng.highAva.spring.TestSupport;
import com.aifeng.highAva.spring.bean.Service.UserService;
import com.aifeng.highAva.spring.bean.domain.Bird;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 11:17
 */
public class TestBean extends TestSupport {
    @Autowired
    private UserService userService;

    @Autowired
    private Bird bird;

    @Test
    public void sayHello() {
        String syaHello = userService.getPersonal().sayHello();
        System.out.println(syaHello);
        System.out.println(bird);
    }
}
