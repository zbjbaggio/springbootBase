package com.springboot.base.data.enmus;

/**
 * 用户状态
 * Created by jay on 2016-11-1.
 */
public enum UserStatus {

    UNACTIVATED((byte)0, "未激活"), DEFAULT((byte)1, "正常"), FREEZE((byte)2, "冻结");

    private byte index;

    private String name;

    UserStatus(byte index, String name) {
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
