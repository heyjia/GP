package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UserForm;

public interface UserService {
    UserDO getOneUserByUserName(String userName);

    boolean addUserByForm(UserForm userForm);

    UserDO getOneUserByEmail(String email);

    UserDO getOneUserByUserId(String inputUserId);
}
