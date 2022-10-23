package com.snail.officialaccount.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/18 22:02
 */
@Slf4j
@Aspect
@Component
public class InterfaceAop {

    /**
     * 记录请求
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(public * com.snail.officialaccount.controller*..*(..))")
    public Object printRequestResponse(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg != null) {
            log.info("{}: {}", arg.getClass().getName(), arg);
            }
        }
        Object response = point.proceed();
        log.info("response: {}", response.toString());
        return response;
    }
}
