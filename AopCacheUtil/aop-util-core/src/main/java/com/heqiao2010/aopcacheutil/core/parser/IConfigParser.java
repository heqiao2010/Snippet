package com.heqiao2010.aopcacheutil.core.parser;

import org.aspectj.lang.ProceedingJoinPoint;

import com.heqiao2010.aopcacheutil.core.Config;
import com.heqiao2010.aopcacheutil.core.annotation.Cache;
import com.heqiao2010.aopcacheutil.core.annotation.Evict;
import com.heqiao2010.aopcacheutil.core.annotation.Update;

public interface IConfigParser {
    public Config parseCacheConfig(ProceedingJoinPoint joinPoint, Cache cache);

    public Config parseUpdateConfig(ProceedingJoinPoint joinPoint, Update update);

    public Config parseEvictConfig(ProceedingJoinPoint joinPoint, Evict evict);
}
