package com.springboot.base.util;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

/**
 *
 * Created by jay on 16-3-19.
 */
@Component
@Singleton
@Log4j
public class ValueHolder  {

    private ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

    public Long getUserIdHolder() {
        return userIdHolder.get();
    }

    public void setUserIdHolder(Long userIdHolder) {
        this.userIdHolder.set(userIdHolder);
    }

}
