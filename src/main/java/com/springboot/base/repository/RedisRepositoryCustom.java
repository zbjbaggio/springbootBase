package com.springboot.base.repository;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * redis
 * Created by jay on 16-3-19.
 */
public interface RedisRepositoryCustom {

    void saveSession(Serializable sessionId, Session session);

    Session getSession(Serializable sessionId);

    void remove(Serializable id);

    String get(String key);

    void put(String key, String value);

    int size();

    <K> Set<K> keys();

    <V> Collection<V> values();
}
