package com.heihei.bookrecommendsystem.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {
    private static final String SMTP_126 = "smtp.126.com";
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String EMAIL_FROM = "heihei_007@126.com";
    private static final String USERNAME = "图书推荐系统";
    private static final String AUTHENTICATION = "WIANTQOMWFMJVXIF";
    private static final String EMAIL_SUBJECT = "注册验证码";
    public static void sendCheckCode(String emailAddress,String code) throws EmailException {
        String msg = "尊敬的用户：\n" +
                "您好，您本次注册的验证码为：" + code;
        String subject = "注册验证码";
        sendEmail(emailAddress,msg,subject);
    }
    public static void sendEmail(String emailAddress,String content,String subject) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_126);
        email.setCharset(CHARSET_UTF8);
        email.addTo(emailAddress);
        email.setFrom(EMAIL_FROM,USERNAME);
        email.setAuthentication(EMAIL_FROM,AUTHENTICATION);
        email.setSubject(subject);
        email.setMsg(content);
        email.send();
    }

    public static void main(String[] args) throws EmailException {
        sendCheckCode("314460226@qq.com","123");
    }
}
