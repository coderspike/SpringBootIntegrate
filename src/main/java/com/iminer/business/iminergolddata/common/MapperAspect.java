package com.iminer.business.iminergolddata.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName MapperAspect
 * @Description aop
 * @Author guowenbin
 * @Date 2018/11/20 16:07
 * @Version 1.0
 **/
@Aspect
@Component
public class MapperAspect {

    @AfterReturning("execution(* com.iminer.business.iminergolddata.mapper.*Mapper.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
    }

    @Pointcut("execution(* com.iminer.business.iminergolddata.mapper.*Mapper.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 声明环绕通知
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.nanoTime();
        Object obj = pjp.proceed();
        long end = System.nanoTime();
        Log.info("调用Mapper方法：{}，参数：{}，执行耗时：{}纳秒，耗时：{}毫秒", pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()), (end - begin), (end - begin) / 1000000);
        return obj;
    }
}
