package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.UserBookScoreDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.vo.UserRatingBookDetailVO;
import com.heihei.bookrecommendsystem.result.CodeMsg;
import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.util.PageReq;
import com.heihei.bookrecommendsystem.util.PageResultSet;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String toInfo(Model model, UserDO userDO, @RequestParam("bookId") Integer bookId,PageReq page) {
        logger.info("bookId" + bookId);
        logger.info("user:" + userDO.toString());
        BookDO book = bookService.getBookByBookId(bookId);
        int re = book.getPublishHourse().length();
        logger.info( re+"");
        logger.info("前往图书详情页面，图书信息为：" + book.toString());
        BookClassDO bookClass = bookService.getBookClassByClassId(book.getClassifyMainId());
        logger.info("前往图书详情页面,图书种类信息为：" + bookClass.toString());
        //查看图书分类
        //获取书的评论详情，默认第一页
        PageResultSet pageResultSet = bookService.getAllBookRateByBookId(page,bookId);
        List<UserRatingBookDetailVO> vos = (List<UserRatingBookDetailVO>)pageResultSet.getDataList();
        for (UserRatingBookDetailVO vo : vos) {
            logger.info("vo:" + vo.toString());
        }
        model.addAttribute("u",userDO);
        model.addAttribute("book",book);
        model.addAttribute("class",bookClass);
        model.addAttribute("pages",pageResultSet);
        return "info";
    }

    @RequestMapping(value = "/userRateBook")
    @ResponseBody
    public Result<Boolean> userRateBook(UserBookScoreDO userBookScore) {
        logger.info("用户评论书籍：" + userBookScore.toString());
        int num = bookService.userRateBook(userBookScore);
        if (num <= 0) {
            return Result.error(CodeMsg.SYSTEM_ERROR);
        }
        return Result.success(true);
    }
}
