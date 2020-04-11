package com.github.heqiao2010.executor.queue_cached_executor.utils;

import lombok.Getter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class NamedThreadFactory implements ThreadFactory {
    private AtomicInteger numOfThreads = new AtomicInteger(0);
    private String threadPrefix;

    private NamedThreadFactory(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(String.join("-",
                threadPrefix, String.valueOf(numOfThreads.getAndIncrement())));
    }

    public static NamedThreadFactory create(String threadPrefix) {
        return new NamedThreadFactory(threadPrefix);
    }
}
