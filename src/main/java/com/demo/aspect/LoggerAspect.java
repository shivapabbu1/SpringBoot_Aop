package com.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggerAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(* com.demo.controller.UserController.*(..))")
    public void loggerpointcut() {}
    
    @Before("loggerpointcut()")
    public void before( JoinPoint joinPoint) {
    	logger.info("Before method invoke:"+joinPoint.getSignature());
    }
    
    @After("loggerpointcut()")
    public void after( JoinPoint joinPoint) {
    	logger.info("After method invoke:"+joinPoint.getSignature());
    }
}
