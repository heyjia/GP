package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UpdateUserForm;
import com.heihei.bookrecommendsystem.result.CodeMsg;
import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.service.UserService;
import com.heihei.bookrecommendsystem.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/toCenter")
    public String toCenter(UserDO userDO, Model model) {
        logger.info("前往个人中心，用户信息为："+ userDO.toString());
        List<BookClassDO> allBookClass = bookService.getAllBookClass();
        model.addAttribute("class",allBookClass);
        model.addAttribute("u",userDO);
        return "PersonalCenter";
    }

    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Result<Boolean> updateUser(UpdateUserForm form) throws ParseException {
        logger.info("更新用户，输入用户信息为：" + form.toString());
        UserDO user  = userService.getOneUserByUserId(form.getUserId());
        if (user == null) {
            return Result.error(CodeMsg.UNKNOW_ACCOUNT);
        }
        boolean reuslt = userService.updateUser(form);
        if (reuslt == false) {
            return Result.error(CodeMsg.UPDATE_USER_ERROR);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/updateUserPswd")
    @ResponseBody
    public Result<Boolean> updateUserPswd(String userId,String opswd,String npswd) {
        logger.info("修改用户密码：userId-" + userId + "旧密码-" + opswd + "新密码-" +  npswd);
        UserDO user = userService.getOneUserByUserId(userId);
        if (user == null) {
            return Result.error(CodeMsg.UNKNOW_ACCOUNT);
        }
        opswd = RSAUtil.decrypt(opswd,RSAUtil.PRIVATE_KEY);
        String dbpswd = RSAUtil.decrypt(user.getPassword(),RSAUtil.PRIVATE_KEY);
        logger.info("解密后的旧密码:" + opswd);
        logger.info("解密后的数据库密码:" + dbpswd);
        if (!dbpswd.equals(opswd)) {
            return Result.error(CodeMsg.OLD_PASSWORD_ERROR);
        }
        boolean result = userService.updateUserPswd(userId,npswd);
        if (result == false) {
            return Result.error(CodeMsg.UPDATE_PASSWORD_ERROR);
        }
        return Result.success(true);
    }
}
