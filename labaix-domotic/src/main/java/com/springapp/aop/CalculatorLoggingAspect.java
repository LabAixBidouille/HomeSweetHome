package com.springapp.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
public class CalculatorLoggingAspect {

    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CalculatorLoggingAspect.class);

    @AfterThrowing(
        pointcut = "execution(* *.*(..))",
        throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOG.trace("Exception " + e + " lancée dans "
            + joinPoint.getSignature().getName() + "()");
    }

}





