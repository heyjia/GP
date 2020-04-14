package com.heihei.bookrecommendsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heihei.bookrecommendsystem.dao.*;
import com.heihei.bookrecommendsystem.entity.*;
import com.heihei.bookrecommendsystem.entity.vo.BookAndClassVO;
import com.heihei.bookrecommendsystem.entity.vo.UserRatingBookDetailVO;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.util.PageReq;
import com.heihei.bookrecommendsystem.util.PageResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookClassMapper bookClassMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserBookScoreMapper userBookScoreMapper;

    @Autowired
    private RecommendItemMapper recommendItemMapper;

    @Autowired
    private RecommendUserMapper recommendUserMapper;
    @Override
    public List<BookClassDO> getAllBookClass() {
        return bookClassMapper.selectAll();
    }

    @Override
    public BookDO getBookByBookId(Integer bookId) {
        logger.info("id:" + bookId);
        BookDO query = new BookDO();
        query.setId(bookId);
        BookDO book = bookMapper.selectByPrimaryKey(query);
        return book;
    }

    @Override
    public BookClassDO getBookClassByClassId(Integer classifyMainId) {
        BookClassDO query = new BookClassDO();
        query.setId(classifyMainId);
        BookClassDO result = bookClassMapper.selectOne(query);
        return result;
    }

    @Override
    public int userRateBook(UserBookScoreDO userBookScore) {
        userBookScore.setUpdtTime(new Date());
        return userBookScoreMapper.insert(userBookScore);
    }

    /**
     * 分页查询
     * @param page
     * @param bookId
     * @return
     */
    @Override
    public PageResultSet getAllBookRateByBookId(PageReq page, Integer bookId) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<UserRatingBookDetailVO> vos = (Page<UserRatingBookDetailVO>)userBookScoreMapper.getAllBookRateByBookId(bookId);
        PageInfo<UserRatingBookDetailVO> voList = vos.toPageInfo();
        PageResultSet pageResultSet = new PageResultSet();
        pageResultSet.setCount(voList.getTotal());
        pageResultSet.setDataList(voList.getList());
        pageResultSet.setLimit(voList.getSize());
        pageResultSet.setPage(voList.getPageNum());
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksBySelKey(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksBySelKey(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public Integer countBookBySelKey(String selKey) {
        Integer num = bookMapper.countBookBySelKey(selKey);
        return num;
    }

    @Override
    public PageResultSet getBooksBySelKeySortByScore(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksBySelKeySortByScore(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksBySelKeySortByWordCount(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksBySelKeySortByWordCount(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksByBookNameSortByScore(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByBookNameSortByScore(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksByBookNameSortByWordCount(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByBookNameSortByWordCount(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksByBookName(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByBookName(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public List<UserRatingBookDetailVO> getAllBooksRateByBookId(Integer bookId) {
        List<UserRatingBookDetailVO> list = userBookScoreMapper.getAllBookRateByBookId(bookId);
        return list;
    }

    @Override
    public PageResultSet getBooksByClass(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByClass(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksByClassSortByWordCount(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByClassSortByWordCount(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public PageResultSet getBooksByClassSortByScore(PageReq page, String selKey) {
        PageHelper.startPage(page.getPage(),page.getLimit());
        Page<BookAndClassVO> vos = (Page<BookAndClassVO>)bookMapper.getBooksByClassSortByScore(selKey);
        PageResultSet pageResultSet = getPageResultSet(vos);
        return pageResultSet;
    }

    @Override
    public List<BookDO> getRandomBooks(int[] randomBookId) {
        List<BookDO> books = bookMapper.getRandomBooks(randomBookId);
        return books;
    }

    @Override
    public List<BookDO> getHotBooks() {
        List<BookDO> books = bookMapper.getHotBooks();
        return books;
    }

    @Override
    public List<BookDO> getRecommendBookByItemByBookId(Integer bookId) {
        RecommendItemDO query = new RecommendItemDO();
        query.setBookIdFrom((long)bookId);
        List<RecommendItemDO> recommendItemList = recommendItemMapper.select(query);
        List<BookDO> recommentBookList = new ArrayList<>();
        for (RecommendItemDO r : recommendItemList) {
            logger.info("喜欢ID为" + r.getBookIdFrom() + "这本书的人还喜欢ID为" + r.getBookIdTo() + "这本书");
            BookDO queryBook = new BookDO();
            long id = r.getBookIdTo();
            queryBook.setId((int)id);
            BookDO book = bookMapper.selectByPrimaryKey(id);
            recommentBookList.add(book);
        }
        for (BookDO b : recommentBookList) {
            logger.info("推荐图书信息为:" + b.toString());
        }
        return recommentBookList;
    }

    @Override
    public List<BookDO> getRecommendBookByUserByUserId(Integer userId) {
        RecommendUserDO query = new RecommendUserDO();
        query.setUserId((long)userId);
        List<RecommendUserDO> recommendUserList = recommendUserMapper.select(query);
        List<BookDO> recommentBookList = new ArrayList<>();
        for (RecommendUserDO r : recommendUserList) {
            logger.info("用户ID为" + r.getUserId() + "还喜欢ID为" + r.getBookId() + "这本书");
            BookDO queryBook = new BookDO();
            long id = r.getBookId();
            queryBook.setId((int)id);
            BookDO book = bookMapper.selectByPrimaryKey(id);
            recommentBookList.add(book);
        }
        for (BookDO b : recommentBookList) {
            logger.info("推荐图书信息为:" + b.toString());
        }
        return recommentBookList;
    }

    private PageResultSet getPageResultSet(Page<BookAndClassVO> vos) {
        PageInfo<BookAndClassVO> voList = vos.toPageInfo();
        PageResultSet pageResultSet = new PageResultSet();
        pageResultSet.setCount(voList.getTotal());
        pageResultSet.setDataList(voList.getList());
        pageResultSet.setLimit(voList.getSize());
        pageResultSet.setPage(voList.getPageNum());
        return pageResultSet;
    }
}
