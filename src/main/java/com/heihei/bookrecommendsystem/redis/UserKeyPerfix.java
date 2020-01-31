package com.heihei.bookrecommendsystem.redis;

public class UserKeyPerfix extends BaseKeyPerfix{
    private UserKeyPerfix(String perfix) {
        super(perfix);
    }

    private UserKeyPerfix(int expireSeconds, String perfix) {
        super(expireSeconds, perfix);
    }

    public static UserKeyPerfix userKeyPerfix = new UserKeyPerfix(300,"tk-"); //默认300秒
}
