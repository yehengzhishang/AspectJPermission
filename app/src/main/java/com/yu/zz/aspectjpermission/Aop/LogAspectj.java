package com.yu.zz.aspectjpermission.Aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 用来测试代码方便理解
 */

@Aspect
public class LogAspectj {


    @Pointcut(("execution(@com.yu.zz.annotation.LogTest * *(..))"))//切点设置 为所有被LogTest标记的方法
    public void methodAroud(){

    }

    @Around("methodAroud()")//anroud 为替换方法，
    public Object aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {

        Log.e("TAG","方法执行前");
        Object result = joinPoint.proceed();//执行原方法
        Log.e("TAG","方法执行后");
        return result; //这里设定为返回值，是防止原方法中有返回值，并且与原方法加Void 无冲突
    }
}
