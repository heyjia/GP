package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.BookDO;

import java.util.List;

public interface BookService {
    List<BookClassDO> getAllBookClass();

    BookDO getBookByBookId(Integer bookId);
}
