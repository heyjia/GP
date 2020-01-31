package com.heihei.bookrecommendsystem.redis;

public interface KeyPerfix {
    int getExpireSeconds();
    String getPerfix();
}
