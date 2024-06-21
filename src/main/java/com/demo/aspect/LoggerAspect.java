package com.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.entity.UserEntity;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(* com.demo.controller.UserController.*(..))")
    public void loggerpointcut() {
    }

    @AfterReturning(pointcut = "loggerpointcut()", returning = "userEntity")
    public void afterReturning(JoinPoint joinPoint, UserEntity userEntity) {
        logger.info("After returning from method: " + joinPoint.getSignature());
        if (userEntity instanceof UserEntity) {
            logger.info("Returned value: " + ((UserEntity) userEntity));
        } else {
            logger.info("Returned value: " + userEntity);
        }
    }

    @AfterThrowing(pointcut = "loggerpointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        logger.error("Exception thrown from method: " + joinPoint.getSignature());
        logger.error("Exception message: " + e.getMessage());
    }

    @After("loggerpointcut()")
    public void after(JoinPoint joinPoint) {
        logger.info("After method invoke: " + joinPoint.getSignature());
    }
}
