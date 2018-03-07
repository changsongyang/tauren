/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.aop;

import java.lang.reflect.Method;

import cn.tauren.framework.aop.api.ProxyInterceptor;
import cn.tauren.framework.ioc.annotation.Bean;

/**
 * 
 * @author HuHui
 * @version $Id: ProcessTimeProxy.java, v 0.1 2017年11月23日 下午12:17:43 HuHui Exp $
 */
@Bean
public class ProcessTimeProxy extends ProxyInterceptor {

    private long start;

    @Override
    protected void before(Class<?> targetClass, Method method, Object[] args) {
        start = System.currentTimeMillis();
    }

    @Override
    protected void after(Class<?> targetClass, Method method, Object[] args) {
        long processTime = System.currentTimeMillis() - start;
        System.out.println(targetClass.getSimpleName() + "." + method.getName() + "执行时间：" + processTime);
    }

}
