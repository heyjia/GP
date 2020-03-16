package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.UpdateUserForm;
import com.heihei.bookrecommendsystem.result.CodeMsg;
import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.service.UserService;
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
}
