package com.springboot.base.repository.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.base.repository.RedisRepositoryCustom;
import com.springboot.base.constant.RedisConstants;
import com.springboot.base.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作
 * Created by jay on 16-3-19.
 */
@Repository
public class RedisRepositoryCustomImpl implements RedisRepositoryCustom {

    @Value("${LoginSessionTime}")
    private int loginSessionTime;

    @Autowired
    StringRedisTemplate template;

    private void save(String key, Object value, long time) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, JSON.toJSONString(value), time, TimeUnit.MINUTES);
    }

    private void save(String key, Object value, long time, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, JSON.toJSONString(value), time, timeUnit);
    }

    public void put(String key, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value);
    }

    @Override
    public int size() {
        return template.keys(RedisConstants.LOGIN + "*").size();
    }

    @Override
    public <K> Set<K> keys() {
        return (Set<K>) template.keys(RedisConstants.LOGIN + "*");
    }

    @Override
    public <V> Collection<V> values() {
        Set<String> keys = template.keys(RedisConstants.LOGIN + "*");
        List list = new ArrayList<V>();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public String get(String key) {
        ValueOperations<String, String> ops = template.opsForValue();
        return ops.get(key);
    }

    private <T> T get(String key, Class<T> clazz) {
        String value = get(key);
        T t = null;
        if (value != null && value.length() > 0) {
            t = JSON.parseObject(value, clazz);
        }
        return t;
    }

    @Override
    public void saveSession(Serializable sessionId, Session session) {
        save(RedisConstants.LOGIN + sessionId, SerializableUtils.serialize(session), loginSessionTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public Session getSession(Serializable sessionId) {
        String s = get(RedisConstants.LOGIN + sessionId);
        if (s == null) {
            return null;
        }
        return SerializableUtils.deserialize(s);
    }

    @Override
    public void remove(Serializable sessionId) {
        template.delete(RedisConstants.LOGIN + sessionId);
    }
}
