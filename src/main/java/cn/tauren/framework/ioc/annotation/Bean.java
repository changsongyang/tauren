/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被@Bean修饰的类表明将会被IoC容器接管
 * @author HuHui
 * @version $Id: Bean.java, v 0.1 2017年11月14日 下午7:35:41 HuHui Exp $
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

    /**
     * <p>被注解修饰的类的名字</p>
     * <p>如果value为空,则框架自动生成以类名首字母小写的名字作为value的值</p>
     * 
     */
    String value() default "";

}
