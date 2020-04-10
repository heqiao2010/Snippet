package com.heqiao2010.aopcacheutil.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 删除缓存,不会拦截方法
 * 
 * @author heqiao
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Evict {
    /**
     * 缓存的命名空间
     * 
     * @return
     */
    String name() default "";

    /**
     * 缓存Key
     * 
     * @return
     */
    Key[] keys() default {};
}
