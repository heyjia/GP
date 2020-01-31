package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.UserMapper;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UserForm;
import com.heihei.bookrecommendsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
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
}
