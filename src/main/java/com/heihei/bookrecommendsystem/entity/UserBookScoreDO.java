package com.heihei.bookrecommendsystem.entity;

import java.util.Date;

public class UserBookScoreDO {
    private int id;
    private int userId;
    private long bookId;
    private int secre;
    private String describe;
    private Date updtTime;

    public UserBookScoreDO() {
    }

    public UserBookScoreDO(int id, int userId, long bookId, int secre, String describe, Date updtTime) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.secre = secre;
        this.describe = describe;
        this.updtTime = updtTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getSecre() {
        return secre;
    }

    public void setSecre(int secre) {
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
