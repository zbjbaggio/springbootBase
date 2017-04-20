package com.springboot.base.shiro;

import com.springboot.base.repository.RedisRepositoryCustom;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 会话持久化
 * Created by jay on 2017-4-13.
 */
@Log4j
public class SessionDao extends EnterpriseCacheSessionDAO {

    @Autowired
    private RedisRepositoryCustom redisRepositoryCustom;

    @Override
    protected void doUpdate(Session session) {
       // log.info("update reids--------------------------------------- sessionId:" + session.getId());
/*       if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
           log.info("过期");
            return; //如果会话过期/停止 没必要再更新了
        }*/
        super.doUpdate(session);
        redisRepositoryCustom.saveSession(session.getId(), session);
        //redisRepositoryCustom.save();
    }

    @Override
    protected void doDelete(Session session) {
        //log.info("delete reids--------------------------------------- sessionId:" + session.getId());
        super.doDelete(session);
        redisRepositoryCustom.remove(session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = super.doCreate(session);
        //log.info("create redis----------------********************* sessionId :" + serializable);
        redisRepositoryCustom.saveSession(serializable, session);
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
       // log.info("读取redis----------------********************* session-id :" + sessionId);
        Session session = super.doReadSession(sessionId);
        if (session != null) {
            return session;
        } else {
            session = redisRepositoryCustom.getSession(sessionId);
        }
        return session;
    }
}
