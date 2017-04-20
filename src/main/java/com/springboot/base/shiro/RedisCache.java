package com.springboot.base.shiro;

import com.springboot.base.repository.RedisRepositoryCustom;
import com.springboot.base.constant.RedisConstants;
import com.springboot.base.util.SerializableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * 暂存操作
 * Created by jay on 2017-4-19.
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisRepositoryCustom redisRepositoryCustom;

    public RedisCache(RedisRepositoryCustom redisRepositoryCustom) {
        this.redisRepositoryCustom = redisRepositoryCustom;
    }

    @Override
    public V get(K key) throws CacheException {
        log.info("get -----------" + key);
        try {
            if (key == null) {
                return null;
            } else {
                String rawValue = redisRepositoryCustom.get(key.toString());
                if (rawValue == null) {
                    return null;
                }
                @SuppressWarnings("unchecked")
                V value = (V) SerializableUtils.deserialize(rawValue);
                System.out.println(value);
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        redisRepositoryCustom.put(key.toString(), SerializableUtils.serialize(value));
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v = get(key);
        redisRepositoryCustom.remove(key.toString());
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisRepositoryCustom.remove(RedisConstants.LOGIN + "*");
    }

    @Override
    public int size() {
        return redisRepositoryCustom.size();
    }

    @Override
    public Set<K> keys() {
        return redisRepositoryCustom.keys();
    }

    @Override
    public Collection<V> values() {
        return redisRepositoryCustom.values();
    }
}
