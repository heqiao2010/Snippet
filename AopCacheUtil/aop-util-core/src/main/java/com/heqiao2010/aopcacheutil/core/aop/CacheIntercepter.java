package com.heqiao2010.aopcacheutil.core.aop;

import com.heqiao2010.aopcacheutil.core.Config;
import com.heqiao2010.aopcacheutil.core.annotation.Cache;
import com.heqiao2010.aopcacheutil.core.annotation.Evict;
import com.heqiao2010.aopcacheutil.core.annotation.Update;
import com.heqiao2010.aopcacheutil.core.cache.ICache;
import com.heqiao2010.aopcacheutil.core.parser.IConfigParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 缓存拦截器
 * 
 * @author heqiao
 *
 */
@Aspect
@Component
public class CacheIntercepter {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private IConfigParser parser;

    @Autowired
    private ICache cache;
    
    @Around("@annotation(com.heqiao2010.aopcacheutil.core.annotation.Cache) && @annotation(c)")
    public Object processCacheMethod(ProceedingJoinPoint joinPoint, Cache c) throws Throwable {
        Object cacheObj = null;
        Config config = null;
        try {
            config = parser.parseCacheConfig(joinPoint, c);
            Assert.notNull(config, "No config found!");
            cacheObj = cache.get(config.getRawKey());
        } catch (Exception e) {
            log.warn("", e);
        }
        if (null == cacheObj) {
            // cache miss
            Object obj = joinPoint.proceed();
            try {
                cache.add(config.getRawKey(), obj, config.getTtl(), config.getUnit());
            } catch (Exception e) {
                log.warn("", e);
            }
            return obj;
        } else {
            // cache hit
            return cacheObj;
        }
    }

    @Around("@annotation(com.heqiao2010.aopcacheutil.core.annotation.Evict) && @annotation(evict)")
    public Object processEvictMethod(ProceedingJoinPoint joinPoint, Evict evict) throws Throwable {
        Object obj = joinPoint.proceed();
        try {
            Config config = parser.parseEvictConfig(joinPoint, evict);
            Assert.notNull(config, "No config found!");
            cache.delete(config.getRawKey());
        } catch (Exception e) {
            log.warn("", e);
        }
        return obj;
    }

    @Around("@annotation(com.heqiao2010.aopcacheutil.core.annotation.Update) && @annotation(update)")
    public Object processUpdateMethod(ProceedingJoinPoint joinPoint, Update update) throws Throwable {
        Object obj = joinPoint.proceed();
        try {
            Config config = parser.parseUpdateConfig(joinPoint, update);
            Assert.notNull(config, "No config found!");
            if (null != obj) {
                cache.update(config.getRawKey(), obj, config.getTtl(), config.getUnit());
            }
        } catch (Exception e) {
            log.warn("", e);
        }
        return obj;
    }
}
