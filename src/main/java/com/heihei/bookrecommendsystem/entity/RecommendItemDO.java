package com.heihei.bookrecommendsystem.entity;

import javax.persistence.Table;

@Table(name = "recommend_item")
public class RecommendItemDO {
    private Long id;
    private Long bookIdFrom;
    private Long bookIdTo;
    private Float val;

    public RecommendItemDO() {
    }

    public RecommendItemDO(Long id, Long bookIdFrom, Long bookIdTo, Float val) {
        this.id = id;
        this.bookIdFrom = bookIdFrom;
        this.bookIdTo = bookIdTo;
        this.val = val;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookIdFrom() {
        return bookIdFrom;
    }

    public void setBookIdFrom(Long bookIdFrom) {
        this.bookIdFrom = bookIdFrom;
    }

    public Long getBookIdTo() {
        return bookIdTo;
    }

    public void setBookIdTo(Long bookIdTo) {
        this.bookIdTo = bookIdTo;
    }

    public Float getVal() {
        return val;
    }

    public void setVal(Float val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "RecommendItemDO{" +
                "id=" + id +
                ", bookIdFrom=" + bookIdFrom +
                ", bookIdTo=" + bookIdTo +
                ", val=" + val +
                '}';
    }
}
