package com.heihei.bookrecommendsystem.entity;

import java.util.Date;

public class UserBookScoreDO {
    private Integer id;
    private Integer userId;
    private long bookId;
    private Integer secre;
    private String describe;
    private Date updtTime;

    public UserBookScoreDO() {
    }

    public UserBookScoreDO(Integer id, Integer userId, long bookId, Integer secre, String describe, Date updtTime) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.secre = secre;
        this.describe = describe;
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

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Integer getSecre() {
        return secre;
    }

    public void setSecre(Integer secre) {
        this.secre = secre;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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
                ", secre=" + secre +
                ", describe='" + describe + '\'' +
                ", updtTime=" + updtTime +
                '}';
    }
}
