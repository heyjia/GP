package com.heihei.bookrecommendsystem.dao;

import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.vo.BookAndClassVO;
import org.apache.ibatis.annotations.Param;
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

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND bc.`name` = #{selKey}")
    List<BookAndClassVO> getBooksByClass(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND bc.`name` = #{selKey} ORDER BY b.`words_count` DESC")
    List<BookAndClassVO> getBooksByClassSortByWordCount(String selKey);

    @Select("SELECT b.*,bc.`name` className FROM book b,book_class bc WHERE b.`classify_main_id` = bc.`id` AND bc.`name` = #{selKey} ORDER BY b.`avg_rating_val` DESC")
    List<BookAndClassVO> getBooksByClassSortByScore(String selKey);

    @Select("<script>select * from book where id in <foreach item = 'item' index = 'index' collection = 'randomBookId' open = '(' separator = ',' close=')'>#{item}</foreach></script>")
    List<BookDO> getRandomBooks(@Param("randomBookId") int[] randomBookId);

    @Select("SELECT * FROM book ORDER BY book.`avg_rating_val` DESC LIMIT 0,8")
    List<BookDO> getHotBooks();

    @Select("SELECT AVG(u.`score`) avgScore FROM user_book_score u WHERE u.`book_id` = #{bookId}")
    Double getAvgScoreByBookId(Integer bookId);

    @Select("SELECT * FROM book b WHERE b.`classify_main_id` = #{classId} ORDER BY b.`avg_rating_val` DESC LIMIT 5")
    List<BookDO> getBookByClassIdLimit5(Integer classId);
}
