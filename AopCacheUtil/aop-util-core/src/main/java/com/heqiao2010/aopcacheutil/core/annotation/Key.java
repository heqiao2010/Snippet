package com.heqiao2010.aopcacheutil.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存Key,定义缓存的Key
 * 
 * @author heqiao
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {

    /**
     * 
     * @return
     */
    String value() default "";

}
