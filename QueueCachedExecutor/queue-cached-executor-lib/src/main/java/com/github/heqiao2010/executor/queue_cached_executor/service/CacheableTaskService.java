package com.github.heqiao2010.executor.queue_cached_executor.service;

import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTaskPO;
import com.github.heqiao2010.executor.queue_cached_executor.repository.CacheableTaskQuery;

import java.util.List;

public interface CacheableTaskService {
    int LIMIT = 1000;
    List<CacheableTaskPO> queryLatest();
    List<CacheableTaskPO> queryByType(CacheableTaskQuery query);
    void save(CacheableTaskPO po);
}
