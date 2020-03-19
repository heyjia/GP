package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_book_score")
public class UserBookScoreDO {
    @Id
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Float score;
    private String describeInfo;
    private Date updtTime;

    public UserBookScoreDO() {
    }

    public UserBookScoreDO(Integer id, Integer userId, Integer bookId, Float score, String describeInfo, Date updtTime) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
        this.describeInfo = describeInfo;
        this.updtTime = updtTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getdescribeInfo() {
        return describeInfo;
    }

    public void setdescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public Date getUpdtTime() {
        return updtTime;
    }

    public void setUpdtTime(Date updtTime) {
        this.updtTime = updtTime;
    }

    @Override
    public String toString() {
        return "UserBookScoreDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", score=" + score +
                ", describeInfo='" + describeInfo + '\'' +
                ", updtTime=" + updtTime +
                '}';
    }
}
