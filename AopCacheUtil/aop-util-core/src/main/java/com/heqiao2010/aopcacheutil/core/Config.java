package com.heqiao2010.aopcacheutil.core;

import java.util.concurrent.TimeUnit;

/**
 * cache object detail.
 * 
 * @author heqiao
 *
 */
public class Config {
    private String rawKey;
    
    private long ttl;
    
    private TimeUnit unit;
    //private Map<String, Object> context;

    public String getRawKey() {
        return rawKey;
    }

    public void setRawKey(String rawKey) {
        this.rawKey = rawKey;
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

    @Override
    public String toString() {
        return "Config [rawKey=" + rawKey + ", ttl=" + ttl + ", unit=" + unit + "]";
    }
    
    
    
    
    
}
