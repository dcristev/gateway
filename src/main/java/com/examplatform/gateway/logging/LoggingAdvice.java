package com.examplatform.gateway.logging;

import com.examplatform.gateway.presentation.auth.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    private ObjectMapper mapper = new ObjectMapper();
    private Logger LOGGER = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value = "execution(* com.examplatform.gateway.presentation.controller.*.*(..)) " +
            "|| execution(* com.examplatform.gateway.service.*.*(..))")
    public void servicePresentationPointCut() {
    }

    @Around("servicePresentationPointCut()")
    public Object logging(ProceedingJoinPoint jp) throws Throwable {
        LOGGER.info("Started execute method " + jp.getSignature().getName()
                + " from class " + jp.getTarget().getClass().getName());
        return jp.proceed();
    }

    @Around("execution(* com.examplatform.gateway.presentation.advice.CustomExceptionHandler.*(..))")
    public Object loggingException(ProceedingJoinPoint jp) throws Throwable {
        String className = jp.getTarget().getClass().getName();
        Object object = jp.proceed();
        LOGGER.warn("Response from class " + className + ": " + mapper.writeValueAsString(object));
        return object;
    }

    @Around("execution(* com.examplatform.gateway.service.auth.UserAuthenticationService.loginUser(..))")
    public Object logUserLogin(ProceedingJoinPoint jp) throws Throwable {
        JwtResponse object = (JwtResponse) jp.proceed();
        LOGGER.info("User login with id = " + object.getId());
        return object;
    }

    @Around("execution(* com.examplatform.gateway.service.auth.UserAuthenticationService.registerUser(..))")
    public Object logUserRegister(ProceedingJoinPoint jp) throws Throwable {
        JwtResponse object = (JwtResponse) jp.proceed();
        LOGGER.info("User register with id = " + object.getId());
        return object;
    }

    @AfterThrowing(pointcut = "execution(* com.examplatform.gateway.service.auth.UserAuthenticationService.*(..))",
            throwing = "ex")
    public void logAfterThrowingExceptionInUserAuthenticationService(Exception ex) {
        LOGGER.warn("Exception thrown in UserAuthenticationService: " + ex.getMessage());
    }
}
