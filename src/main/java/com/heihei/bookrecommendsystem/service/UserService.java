package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UpdateUserForm;
import com.heihei.bookrecommendsystem.entity.form.UserForm;

import java.text.ParseException;

public interface UserService {
    UserDO getOneUserByUserName(String userName);

    boolean addUserByForm(UserForm userForm);

    UserDO getOneUserByEmail(String email);

    UserDO getOneUserByUserId(String inputUserId);

    boolean updateUser(UpdateUserForm form) throws ParseException;
}
