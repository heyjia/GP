package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/book")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;
    /**
     * 前往图书详情页面
     */
    @RequestMapping("/toInfo")
    public String toInfo(Model model, UserDO userDO, @RequestParam("bookId") Integer bookId) {
        logger.info("bookId" + bookId);
        logger.info("user:" + userDO.toString());
        BookDO book = bookService.getBookByBookId(bookId);
        logger.info("前往图书详情页面，图书信息为：" + book.toString());
        model.addAttribute("u",userDO);
        model.addAttribute("book",book);
        return "info";
    }
}
