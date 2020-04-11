package com.github.heqiao2010.executor.queue_cached_executor.repository;

import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTaskPO;

import java.util.List;

public interface CacheableTaskRepository {
    List<CacheableTaskPO> query(CacheableTaskQuery query, int limit);
    void save(CacheableTaskPO po);
}
