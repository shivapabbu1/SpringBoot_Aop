package com.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class TransactionAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAspect.class);

    private final PlatformTransactionManager transactionManager;
    private final ThreadLocal<TransactionStatus> currentTransaction = new ThreadLocal<>();

    public TransactionAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Pointcut("execution(* com.demo.service.UserService.saveUser(..))")
    public void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void beginTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        currentTransaction.set(status);
        logger.info("Transaction started: {}", status);
    }

    @After("allServiceMethods()")
    public void commitTransaction() {
        TransactionStatus status = currentTransaction.get();
        if (status != null) {
            transactionManager.commit(status);
            logger.info("Transaction committed: {}", status);
            currentTransaction.remove();
        }
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
    public void rollbackTransaction(Exception exception) {
        TransactionStatus status = currentTransaction.get();
        if (status != null) {
            transactionManager.rollback(status);
            logger.error("Transaction rolled back due to exception: {}", exception.getMessage());
            currentTransaction.remove();
        }
    }
}
