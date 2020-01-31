package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.EmailDO;

public interface EmailService {
    int insertCode(String userName,String emailAddress,String code);

    EmailDO getEmailByAddress(String email,String userName);

    int updateOne(EmailDO emailDO);
}
