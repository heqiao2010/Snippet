package com.github.heqiao2010.executor.queue_cached_executor;


import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTask;

public interface RunnableConverter {
    Runnable convert(CacheableTask task);
    Class<? extends CacheableTask> taskClass();
}
