package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.BookClassMapper;
import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookClassMapper bookClassMapper;
    @Override
    public List<BookClassDO> getAllBookClass() {
        return bookClassMapper.selectAll();
    }
}
