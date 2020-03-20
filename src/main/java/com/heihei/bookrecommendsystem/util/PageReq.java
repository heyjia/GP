package com.heihei.bookrecommendsystem.util;

public class PageReq {
    private int page = 1;
    private int limit = 10;

    public PageReq() {
    }

    public PageReq(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
