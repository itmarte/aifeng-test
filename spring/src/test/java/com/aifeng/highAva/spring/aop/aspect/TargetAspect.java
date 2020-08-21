package com.aifeng.highAva.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 切面
 * @author: imart·deng
 * @date: 2020/4/8 15:07
 */
@Aspect
public class TargetAspect {
    private final Logger logger = LoggerFactory.getLogger(TargetAspect.class);

    @Pointcut("@annotation(com.aifeng.highAva.spring.aop.annotation.LogCut)")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("pointCut before ..........");
        Object obj =joinPoint.proceed();
        logger.debug("pointCut end ..........");
        return obj;
    }
}
