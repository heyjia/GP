package com.heihei.bookrecommendsystem.redis;

public class BaseKeyPerfix implements KeyPerfix{

    private int expireSeconds;

    private String perfix;

    public BaseKeyPerfix(int expireSeconds,String perfix) {
        this.expireSeconds = expireSeconds;
        this.perfix = perfix;
    }

    public BaseKeyPerfix(String perfix) {
        this.expireSeconds = 0;
        this.perfix = perfix;
    }

    @Override
    public int getExpireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPerfix() {
        String className = getClass().getSimpleName();
        return className + ":" + perfix;
    }
}
