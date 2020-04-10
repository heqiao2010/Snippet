package com.heqiao2010.aopcacheutil.core.cache;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

/**
 * 用map实现的简单缓存;
 * 
 * @author heqiao
 *
 */
@Service
public class SimpleMemCacheImpl implements ICache {

    /**
     * 借助ConcurrentHashMap
     */
    private static final Map<String, CacheObject> CACHE = new ConcurrentHashMap<String, CacheObject>();

    public Boolean add(String key, Object obj, long ttl, TimeUnit unit) {
        CacheObject cacheObj = new CacheObject(obj, ttl, unit);
        CACHE.put(key, cacheObj);
        return Boolean.TRUE;
    }

    public Boolean delete(String key) {
        CACHE.remove(key);
        return Boolean.TRUE;
    }

    public Boolean update(String key, Object obj, long ttl, TimeUnit unit) {
        CacheObject cacheObj = new CacheObject(obj, ttl, unit);
        CACHE.put(key, cacheObj);
        return Boolean.TRUE;
    }

    public Object get(String key) {
        CacheObject cacheObj = CACHE.get(key);
        if (null != cacheObj && !cacheObj.isExpired()) {
            return cacheObj.get();
        } else {
            return null;
        }
    }

    /**
     * 软引用保存缓存对象
     * 
     * @author heqiao
     *
     */
    public static class CacheObject extends SoftReference<Object> {

        private long ttl;

        private TimeUnit unit;

        private long createTime = System.currentTimeMillis();

        public CacheObject(Object referent, long ttl, TimeUnit unit) {
            super(referent);
            this.ttl = ttl;
            this.unit = unit;
        }

        public Object get() {
            return super.get();
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        public TimeUnit getUnit() {
            return unit;
        }

        public void setUnit(TimeUnit unit) {
            this.unit = unit;
        }

        public boolean isExpired() {
            return (System.currentTimeMillis() - createTime) > TimeUnit.MILLISECONDS.convert(ttl, unit);
        }

        @Override
        public String toString() {
            return "CacheObject [ttl=" + ttl + ", unit=" + unit + ", createTime=" + createTime + "]";
        }
    }

    /**
     * 单线程清理服务
     */
    private static final ScheduledExecutorService CLEAN_SERVICE = Executors.newSingleThreadScheduledExecutor();

    {
        CLEAN_SERVICE.scheduleAtFixedRate(() -> {
            Iterator<Entry<String, CacheObject>> it = CACHE.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, CacheObject> e = it.next();
                if (null == e.getValue() || null == e.getValue().get() || e.getValue().isExpired()) {
                    CACHE.remove(e.getKey());
                }
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

}
