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

    PageResultSet getBooksBySelKey(PageReq page, String selKey);

    Integer countBookBySelKey(String selKey);

    PageResultSet getBooksBySelKeySortByScore(PageReq page, String selKey);

    PageResultSet getBooksBySelKeySortByWordCount(PageReq page, String selKey);

    PageResultSet getBooksByBookNameSortByScore(PageReq page, String selKey);

    PageResultSet getBooksByBookNameSortByWordCount(PageReq page, String selKey);

    PageResultSet getBooksByBookName(PageReq page, String selKey);

    List<UserRatingBookDetailVO> getAllBooksRateByBookId(Integer bookId);

    PageResultSet getBooksByClass(PageReq page, String selKey);

    PageResultSet getBooksByClassSortByWordCount(PageReq page, String selKey);

    PageResultSet getBooksByClassSortByScore(PageReq page, String selKey);

    List<BookDO> getRandomBooks(int[] randomBookId);

    List<BookDO> getHotBooks();
}
