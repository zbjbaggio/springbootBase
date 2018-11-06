package com.springboot.base.data.enmus;

/**
 * 用户状态
 * Created by jay on 2016-11-1.
 */
public enum OrderStatus {

    UNPAY((byte)1, "待支付"), PAYING((byte)2, "支付中"), PAY_SUCCESS((byte)3, "支付完成"), SUCCESS((byte)4, "订单完成");

    private byte index;

    private String name;

    OrderStatus(byte index, String name) {
        this.index = index;
        this.name = name;
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
