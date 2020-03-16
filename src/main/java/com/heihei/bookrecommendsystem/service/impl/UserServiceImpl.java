package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.UserMapper;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UpdateUserForm;
import com.heihei.bookrecommendsystem.entity.form.UserForm;
import com.heihei.bookrecommendsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDO getOneUserByUserName(String userName) {
        UserDO query = new UserDO();
        query.setName(userName);
        return userMapper.selectOne(query);
    }

    @Override
    public boolean addUserByForm(UserForm userForm) {
        UserDO addUser = new UserDO();
        addUser.setUserId(userForm.getUserId());
        addUser.setName(userForm.getUserName());
        addUser.setPassword(userForm.getPassword());
        addUser.setEmail(userForm.getEmail());
        addUser.setUpdtTime(new Date());
        addUser.setCrtTime(new Date());
        int num = userMapper.insert(addUser);
        if (num <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public UserDO getOneUserByEmail(String email) {
        UserDO query = new UserDO();
        query.setEmail(email);
        return userMapper.selectOne(query);
    }

    @Override
    public UserDO getOneUserByUserId(String userId) {
        UserDO query = new UserDO();
        query.setUserId(userId);
        return userMapper.selectOne(query);
    }

    @Override
    public boolean updateUser(UpdateUserForm form) throws ParseException {
        UserDO user = getOneUserByUserId(form.getUserId());
        user.setEmail(form.getEmail());
        user.setName(form.getUserName());
        user.setAddress(form.getAddress());
        user.setAge(form.getAge());
        user.setSex(form.getSex());
        user.setUpdtTime(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("更新后的用户信息:" + user.toString());
        user.setBirthday(format.parse(form.getBirthday()));
        int num = userMapper.updateByPrimaryKeySelective(user);
        return num > 0;
    }

    @Override
    public boolean updateUserPswd(String userId, String npswd) {
        UserDO user = getOneUserByUserId(userId);
        user.setPassword(npswd);
        int num = userMapper.updateByPrimaryKeySelective(user);
        return num > 0;
    }
}
