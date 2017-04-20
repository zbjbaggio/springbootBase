package com.springboot.base.shiro;

import com.springboot.base.repository.RedisRepositoryCustom;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 暂存管理
 * Created by jay on 2017-4-19.
 */
public class RedisCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    @Autowired
    private RedisRepositoryCustom redisRepositoryCustom;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache c = caches.get(name);
        if (c == null) {
            c = new RedisCache<K, V>(redisRepositoryCustom);
            caches.put(name, c);
        }
        return c;
    }
}
