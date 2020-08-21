package com.aifeng.highAva.spring.bean.Service;

import com.aifeng.highAva.spring.bean.domain.Personal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/7 11:17
 */
@Component
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private Personal personal;

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
