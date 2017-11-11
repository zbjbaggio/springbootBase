package com.springboot.base.data.enmus;

/**
 * 产品状态
 */
public enum ProductStatus {

    ON_SHELVES((byte)1, "上架"), OFF_SHELVES((byte)2, "下架");

    private byte index;

    private String name;

    ProductStatus(byte index, String name) {
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
