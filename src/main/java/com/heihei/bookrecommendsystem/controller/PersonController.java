package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "toCenter")
    public String toCenter(UserDO userDO, Model model) {
        logger.info("前往个人中心，用户信息为："+ userDO.toString());
        List<BookClassDO> allBookClass = bookService.getAllBookClass();
        model.addAttribute("class",allBookClass);
        model.addAttribute("u",userDO);
        return "PersonalCenter";
    }

}
