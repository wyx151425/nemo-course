package com.rumofuture.nemo.context.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Nemo应用切面类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/08/01
 */
//@Aspect
//@Component
//public class NemoAspect {
//
//    private Logger logger = LoggerFactory.getLogger(NemoAspect.class);
//
//    @Pointcut("execution(public * com.rumofuture.nemo..*.*(..))")
//    public void nemoPointcut() {}
//
//    @Pointcut("execution(public * com.rumofuture.nemo.controller.*.*(..))")
//    public void controllerPointcut() {}
//
//    @Before(value = "nemoPointcut()")
//    public void actionBefore(JoinPoint joinPoint) {
//        logger.info(joinPoint.getSignature().getDeclaringTypeName()
//                + ": " +  joinPoint.getSignature().getName() + "-start");
//
////        ServletRequestAttributes attributes =
////                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////        HttpServletRequest request = (HttpServletRequest) attributes.getRequest();
////        request.getRequestURL();
////        request.getMethod();
////        request.getRemoteAddr();
//    }
//
//    @After(value = "nemoPointcut()")
//    public void actionAfter(JoinPoint joinPoint) {
//        logger.info(joinPoint.getSignature().getDeclaringTypeName()
//                + ": " +  joinPoint.getSignature().getName() + "-stop");
//    }
//
//    @AfterReturning(value = "nemoPointcut()", returning = "object")
//    public void actionAfterReturning(JoinPoint joinPoint, Object object) {
//        logger.info(joinPoint.getSignature().getDeclaringTypeName()
//                + ": " +  joinPoint.getSignature().getName() + "-returning");
//        logger.info("Return Value: " + object);
//    }
//
//    @AfterThrowing(value = "controllerPointcut()", throwing = "ex")
//    public void actionAfterThrowing(JoinPoint joinPoint, Exception ex) {
//        logger.info(joinPoint.getSignature().getDeclaringTypeName()
//                + ": " +  joinPoint.getSignature().getName() + "-throwing");
//        logger.error("Throw Exception: ", ex);
//    }
//}
