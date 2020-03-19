package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.BookClassMapper;
import com.heihei.bookrecommendsystem.dao.BookMapper;
import com.heihei.bookrecommendsystem.dao.UserBookScoreMapper;
import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.UserBookScoreDO;
import com.heihei.bookrecommendsystem.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
