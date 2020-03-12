package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/person")
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(PersonController.class);
    @RequestMapping(value = "toCenter")
    public String toCenter(UserDO userDO, Model model) {
        logger.info("前往个人中心，用户信息为："+ userDO.toString());
        model.addAttribute("user",userDO);
        return "PersonalCentere";
    }

}
