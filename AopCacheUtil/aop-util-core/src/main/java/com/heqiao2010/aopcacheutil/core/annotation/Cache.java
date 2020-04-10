package com.heqiao2010.aopcacheutil.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解,会拦截方法执行
 * 
 * @author heqiao
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * 缓存的命名空间
     * 
     * @return
     */
    String name() default "";

    /**
     * 缓存的有效期
     * 
     * @return
     */
    long ttl() default 1;

    /**
     * 缓存有效期单位
     * 
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 缓存Key
     * 
     * @return
     */
    Key[] keys() default {};

}
