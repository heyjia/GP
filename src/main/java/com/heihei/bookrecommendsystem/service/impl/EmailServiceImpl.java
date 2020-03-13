package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.EmailMapper;
import com.heihei.bookrecommendsystem.entity.EmailDO;
import com.heihei.bookrecommendsystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    EmailMapper emailMapper;
    @Override
    public int insertCode(String userId, String emailAddress, String code) {
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress(emailAddress);
        emailDO.setCode(code);
        emailDO.setUserId(userId);
        emailDO.setUpdtTime(new Date());
        emailMapper.insert(emailDO);
        return 1;
    }

    @Override
    public EmailDO getEmailByAddress(String email,String userId) {
        EmailDO query = new EmailDO();
        query.setEmailAddress(email);
        query.setUserId(userId);
        return emailMapper.selectOne(query);
    }

    @Override
    public int updateOne(EmailDO emailDO) {
        return emailMapper.updateByPrimaryKey(emailDO);
    }
}
