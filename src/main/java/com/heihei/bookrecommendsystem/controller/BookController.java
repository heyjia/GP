package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.*;
import com.heihei.bookrecommendsystem.entity.vo.BookAndClassVO;
import com.heihei.bookrecommendsystem.entity.vo.SearchBookVO;
import com.heihei.bookrecommendsystem.entity.vo.UserRatingBookDetailVO;
import com.heihei.bookrecommendsystem.result.CodeMsg;
import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.util.ChartDataJsonCreater;
import com.heihei.bookrecommendsystem.util.PageReq;
import com.heihei.bookrecommendsystem.util.PageResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
        List<BookDO> recommendBookByItemList = bookService.getRecommendBookByItemByBookId(bookId);
        model.addAttribute("recommendBookList",recommendBookByItemList);
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

    @RequestMapping(value = "/searchBook")
    public String searchBook(Model model, UserDO userDO,String selKey,PageReq page,String sortType,String searchType) {
        logger.info("searchType:" + searchType);
        logger.info(sortType);
        logger.info(page.toString());
        logger.info(selKey);
        PageResultSet pageResultSet = null;
        //根据类型排序
        if ("author".equals(searchType)) {
            if ("score".equalsIgnoreCase(sortType)) {
                pageResultSet = bookService.getBooksBySelKeySortByScore(page,selKey);
            }else{
                if ("wordCount".equalsIgnoreCase(sortType)) {
                    pageResultSet = bookService.getBooksBySelKeySortByWordCount(page,selKey);
                }else{
                    pageResultSet = bookService.getBooksBySelKey(page,selKey);
                }
            }
        }else if ("class".equalsIgnoreCase(searchType)) {
                if ("class".equalsIgnoreCase(sortType)) {
                    pageResultSet = bookService.getBooksByClassSortByScore(page,selKey);
                }else{
                    if ("wordCount".equalsIgnoreCase(sortType)) {
                        pageResultSet = bookService.getBooksByClassSortByWordCount(page,selKey);
                    }else{
                        pageResultSet = bookService.getBooksByClass(page,selKey);
                    }
                }
        }else{
                if ("score".equalsIgnoreCase(sortType)) {
                    pageResultSet = bookService.getBooksByBookNameSortByScore(page,selKey);
                }else{
                    if ("wordCount".equalsIgnoreCase(sortType)) {
                        pageResultSet = bookService.getBooksByBookNameSortByWordCount(page,selKey);
                    }else{
                        pageResultSet = bookService.getBooksByBookName(page,selKey);
                    }
                }
            }
        List<BookAndClassVO> vos = (List<BookAndClassVO>)pageResultSet.getDataList();
        for (BookAndClassVO vo : vos) {
            logger.info("vo:" + vo.toString());
        }
        SearchBookVO vo = new SearchBookVO();
        model.addAttribute("key",selKey);
        model.addAttribute("u",userDO);
        model.addAttribute("pages",pageResultSet);
        model.addAttribute("serachType" ,searchType);
        return "searchInfo";
    }

    @RequestMapping(value = "/ratingList/getBookCountPieChartData")
    public void getBookCountPieChartData(Integer bookId, HttpServletResponse response) {
        List<UserRatingBookDetailVO> list = bookService.getAllBooksRateByBookId(bookId);
        double [] data = {0,0,0,0,0};
        for (UserRatingBookDetailVO vo : list) {
            float score = vo.getScore();
            if (score > 4.0) {
                data[4]++;
            }else if (score > 3.0) {
                data[3]++;
            }else if (score > 2.0) {
                data[2]++;
            }else if (score > 1.0) {
                data[1]++;
            }else{
                data[0]++;
            }
        }
        for (double d : data) {
            logger.info(d + "");
        }
        String[] backgroundColor = {
                "rgba(255, 99, 132, 0.7)", // Red
                "rgba(255, 159, 64, 0.7)", // Orange
                "rgba(255, 205, 86, 0.7)", // Yellow
                "rgba(75, 192, 192, 0.7)", // Green
                "rgba(54, 162, 235, 0.7)"}; // Blue
        String[] labels = {
                "很差",
                "较差",
                "还行",
                "推荐",
                "力荐"
        };
        String json;
        try {
            json = ChartDataJsonCreater.getPieJson(data,backgroundColor,labels,"各评分人数占比饼图","left");
            logger.info(json);
            response.setCharacterEncoding("UTF-8"); //设置编码格式
            response.setContentType("text/html; charset=utf-8");//设置数据格式
            PrintWriter out = response.getWriter(); //获取写入对象
            out.print(json); //将json数据写入流中
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
