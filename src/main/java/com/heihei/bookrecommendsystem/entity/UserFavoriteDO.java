package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Table;

@Table(name = "user_favorite")
public class UserFavoriteDO {
    private Integer id;
    private Integer userId;
    private Integer bookClassId;

    public UserFavoriteDO() {
    }

    public UserFavoriteDO(Integer id, Integer userId, Integer bookClassId) {
        this.id = id;
        this.userId = userId;
        this.bookClassId = bookClassId;
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

    public Integer getBookClassId() {
        return bookClassId;
    }

    public void setBookClassId(Integer bookClassId) {
        this.bookClassId = bookClassId;
    }

    @Override
    public String toString() {
        return "UserFavoriteDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookClassId=" + bookClassId +
                '}';
    }
}
