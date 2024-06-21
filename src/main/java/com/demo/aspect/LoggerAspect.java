package com.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.entity.UserEntity;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

//    @Pointcut("execution(* com.demo.controller.UserController.*(..))")
    @Pointcut("within(com.demo.controller.UserController)")
    public void loggerPointcut() {
    }

    @Around("loggerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            logger.info("Before method invoke: " + joinPoint.getSignature());
            result = joinPoint.proceed();
            logger.info("After returning from method: " + joinPoint.getSignature());
            if (result instanceof UserEntity) {
                logger.info("Returned value: " + result);
            }
        } catch (Exception e) {
            logger.error("Exception thrown from method: " + joinPoint.getSignature());
            logger.error("Exception message: " + e.getMessage());
            throw e;  // rethrow the exception to maintain the program flow
        } finally {
            logger.info("After method invoke: " + joinPoint.getSignature());
        }
        return result;
    }
}
