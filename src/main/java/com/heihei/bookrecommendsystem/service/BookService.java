package com.heihei.bookrecommendsystem.service;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.UserBookScoreDO;
import com.heihei.bookrecommendsystem.entity.vo.UserRatingBookDetailVO;
import com.heihei.bookrecommendsystem.util.PageReq;
import com.heihei.bookrecommendsystem.util.PageResultSet;

import java.util.List;

public interface BookService {
    List<BookClassDO> getAllBookClass();

    BookDO getBookByBookId(Integer bookId);

    BookClassDO getBookClassByClassId(Integer classifyMainId);

    int userRateBook(UserBookScoreDO userBookScore);

    PageResultSet getAllBookRateByBookId(PageReq page, Integer bookId);
}
