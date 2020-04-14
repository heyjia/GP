package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.UserFavoriteDO;
import com.heihei.bookrecommendsystem.entity.form.UpdateUserForm;
import com.heihei.bookrecommendsystem.entity.form.UserForm;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    UserDO getOneUserByUserName(String userName);

    boolean addUserByForm(UserForm userForm);

    UserDO getOneUserByEmail(String email);

    UserDO getOneUserByUserId(String inputUserId);

    boolean updateUser(UpdateUserForm form) throws ParseException;

    boolean updateUserPswd(String userId, String npswd);

    List<UserFavoriteDO> getFavoriteByUserId(Integer userId);
}
