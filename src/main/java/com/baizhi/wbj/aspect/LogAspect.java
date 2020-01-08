package com.baizhi.wbj.aspect;

import com.baizhi.wbj.annotation.LogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Aspect
@Component
public class LogAspect {
    @Autowired
    HttpSession session;
//    @Around(value = "execution(* com.baizhi.wbj.service.BannerServiceImpl.*(..))")
    @Around(value = "@annotation(com.baizhi.wbj.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
//        时间
        Date date = new Date();
        System.out.println("date = " + date);
//        人物
        Object currentAdmin = session.getAttribute("currentAdmin");
        System.out.println("currentAdmin = " + currentAdmin);
//        事务
//        获取自定义注解的名字
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        System.out.println("value = " + value);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
