package com.heihei.bookrecommendsystem.dao;


import com.heihei.bookrecommendsystem.entity.UserBookScoreDO;
import com.heihei.bookrecommendsystem.entity.vo.UserRatingBookDetailVO;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


public interface UserBookScoreMapper extends BaseMapper<UserBookScoreDO> {
    @Select("SELECT \n" +
            "u.`user_id` userId, \n" +
            "ubs.`book_id` bookId,\n" +
            "ubs.`score` score, \n" +
            "ubs.`describe_info` describeInfo,\n" +
            "ubs.`updt_time` updtTime,\n" +
            "u.`name` userName\n" +
            "FROM \n" +
            "user_book_score ubs,`user` u \n" +
            "WHERE \n" +
            "ubs.`user_id` = u.`id` AND book_id = 2")
    List<UserRatingBookDetailVO> getAllBookRateByBookId(Integer bookId);
}
