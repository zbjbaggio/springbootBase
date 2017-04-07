package com.springboot.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

/**
 * 用户id
 * Created by jay on 16-10-20.
 */
@Component
@Singleton
public class ValueHolder {

    private static final Logger LOG = LoggerFactory.getLogger(ValueHolder.class);

    private ThreadLocal<String> merchIdHolder = new ThreadLocal<>();

    private ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public String getMerchIdHolder() {
        return merchIdHolder.get();
    }

    public void setMerchIdHolder(String merchIdHolder) {
        this.merchIdHolder.set(merchIdHolder);
    }

    public String getTokenHolder() {
        return tokenHolder.get();
    }

    public void setTokenHolder(String tokenHolder) {
        this.tokenHolder.set(tokenHolder);
    }

}
