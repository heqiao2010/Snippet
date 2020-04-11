package com.github.heqiao2010.executor.queue_cached_executor.service;

import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTaskPO;
import com.github.heqiao2010.executor.queue_cached_executor.repository.CacheableTaskQuery;
import com.github.heqiao2010.executor.queue_cached_executor.repository.CacheableTaskRepository;

import java.util.List;

public class CacheableTaskServiceImpl implements CacheableTaskService {
    private final CacheableTaskRepository cacheableTaskRepository;

    public CacheableTaskServiceImpl(CacheableTaskRepository cacheableTaskRepository) {
        this.cacheableTaskRepository = cacheableTaskRepository;
    }

    @Override
    public List<CacheableTaskPO> queryLatest() {
        return cacheableTaskRepository.query(CacheableTaskQuery.builder().build(), LIMIT);
    }

    @Override
    public List<CacheableTaskPO> queryByType(CacheableTaskQuery query) {
        return cacheableTaskRepository.query(query, LIMIT);
    }

    @Override
    public void save(CacheableTaskPO po) {
        cacheableTaskRepository.save(po);
    }
}
