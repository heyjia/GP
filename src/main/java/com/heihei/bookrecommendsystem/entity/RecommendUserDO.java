package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Table;

@Table(name = "recommend_user")
public class RecommendUserDO {
    private Long id;
    private Long userId;
    private Long bookId;
    private Float val;

    public RecommendUserDO() {
    }

    public RecommendUserDO(Long id, Long userId, Long bookId, Float val) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.val = val;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Float getval() {
        return val;
    }

    public void setval(Float val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "RecommendDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", val=" + val +
                '}';
    }
}
