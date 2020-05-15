package com.heihei.bookrecommendsystem.redis;

public class MailKeyPerfix extends BaseKeyPerfix{
    private MailKeyPerfix(String perfix) {
        super(perfix);
    }

    private MailKeyPerfix(int expireSeconds, String perfix) {
        super(expireSeconds, perfix);
    }

    public static MailKeyPerfix mailKeyPerfix = new MailKeyPerfix(500,"mail-"); //默认300秒
}
