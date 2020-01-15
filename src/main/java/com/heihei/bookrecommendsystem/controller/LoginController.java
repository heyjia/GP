package com.heihei.bookrecommendsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    //前往登录页面
    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    // 前往注册页面
    @RequestMapping(value = "/toRegister")
    public String toRegister() {
        return "register";
    }
}
