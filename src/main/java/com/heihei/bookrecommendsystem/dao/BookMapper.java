package com.heihei.bookrecommendsystem.dao;

import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.vo.BookAndClassVO;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface BookMapper extends BaseMapper<BookDO> {
    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND author LIKE CONCAT('%',#{selKey},'%')")
    List<BookAndClassVO> getBooksBySelKey(String selKey);

    @Select("SELECT COUNT(id) FROM book WHERE author LIKE CONCAT('%',#{selKey},'%')")
    Integer countBookBySelKey(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND author LIKE CONCAT('%',#{selKey},'%') ORDER BY b.`avg_rating_val` DESC")
    List<BookAndClassVO> getBooksBySelKeySortByScore(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND author LIKE CONCAT('%',#{selKey},'%') ORDER BY b.`words_count` DESC")
    List<BookAndClassVO> getBooksBySelKeySortByWordCount(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND b.name LIKE CONCAT('%',#{selKey},'%') ORDER BY b.`avg_rating_val` DESC")
    List<BookAndClassVO> getBooksByBookNameSortByScore(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND b.name LIKE CONCAT('%',#{selKey},'%') ORDER BY b.`words_count` DESC")
    List<BookAndClassVO> getBooksByBookNameSortByWordCount(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND b.name LIKE CONCAT('%',#{selKey},'%')")
    List<BookAndClassVO> getBooksByBookName(String selKey);
}
