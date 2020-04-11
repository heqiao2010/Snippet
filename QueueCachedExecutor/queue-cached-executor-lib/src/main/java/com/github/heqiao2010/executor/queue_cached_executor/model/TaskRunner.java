package com.github.heqiao2010.executor.queue_cached_executor.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskRunner implements Runnable {
    private final CacheableTask task;
    private final Runnable delegate;

    public TaskRunner(Runnable delegate, CacheableTask task) {
        this.task = task;
        this.delegate = delegate;
    }

    @Override
    public void run() {
        delegate.run();
    }
}
