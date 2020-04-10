package com.heqiao2010.aopcacheutil.core.cache;

import java.util.concurrent.TimeUnit;

/**
 * cache interface
 * 
 * @author heqiao
 *
 */
public interface ICache {

    public Boolean add(String key, Object obj, long ttl, TimeUnit unit);

    public Boolean delete(String key);

    public Boolean update(String key, Object obj, long ttl, TimeUnit unit);

    public Object get(String key);

}
