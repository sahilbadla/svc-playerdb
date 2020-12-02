package com.intuit.playerdb.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private SimpleLogger LOGGER;

    @Pointcut("execution(public * com.intuit.playerdb.service..*(..))")
    public void allMethods() {
    }

    @Before("allMethods()")
    public void before(JoinPoint joinPoint) {
        LOGGER = new SimpleLogger(LoggerFactory.getLogger(joinPoint.getTarget().getClass()));
        LOGGER.trace("[Enter : {}] with args {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "allMethods()", returning = "returnValue")
    public void after(JoinPoint joinPoint, Object returnValue) {
        LOGGER = new SimpleLogger(LoggerFactory.getLogger(joinPoint.getTarget().getClass()));
        Signature signature = joinPoint.getSignature();
        LOGGER.trace("[Exit : {}] with return type [{}] and value [{}]", signature.toShortString(),
                ((MethodSignature) signature).getReturnType(), returnValue);
    }
}
