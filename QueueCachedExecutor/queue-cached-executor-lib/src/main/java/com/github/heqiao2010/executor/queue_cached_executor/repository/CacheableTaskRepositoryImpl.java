package com.github.heqiao2010.executor.queue_cached_executor.repository;

import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTaskPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CacheableTaskRepositoryImpl implements CacheableTaskRepository {

    protected final MongoTemplate template;
    protected final String collection;

    public CacheableTaskRepositoryImpl(MongoTemplate template) {
        this.template = template;
        this.collection = CacheableTaskPO.COLLECTION;
    }

    private Criteria createCriteria(CacheableTaskQuery query) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotEmpty(query.getType())) {
            criteria.and(CacheableTaskPO.FIELD_TYPE).is(query.getType());
        }
        if (query.isRequeue()) {
            criteria.and(CacheableTaskPO.FIELD_REQUEUE_TIME).ne(null);
        } else {
            criteria.and(CacheableTaskPO.FIELD_REQUEUE_TIME).is(null);
        }
        return criteria;
    }

    @Override
    public List<CacheableTaskPO> query(CacheableTaskQuery query, int limit) {
        Query q = Query.query(createCriteria(query))
                .limit(limit)
                .with(new Sort(Sort.Direction.ASC, CacheableTaskPO.FIELD_CREATE_TIME));
        return template.find(q, CacheableTaskPO.class);
    }

    @Override
    public void save(CacheableTaskPO po) {
        template.save(po);
    }
}
