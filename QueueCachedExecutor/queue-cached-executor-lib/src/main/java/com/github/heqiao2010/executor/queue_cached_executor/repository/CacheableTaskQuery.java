package com.github.heqiao2010.executor.queue_cached_executor.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CacheableTaskQuery {
    private String type;
    private boolean isRequeue;
}
