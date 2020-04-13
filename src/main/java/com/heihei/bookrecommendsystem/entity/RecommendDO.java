package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Table;

@Table(name = "recommend")
public class RecommendDO {
    private Long id;
    private Long userId;
    private Long bookId;
    private Float score;

    public RecommendDO() {
    }

    public RecommendDO(Long id, Long userId, Long bookId, Float score) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RecommendDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", score=" + score +
                '}';
    }
}
