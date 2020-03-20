package com.heihei.bookrecommendsystem.entity.vo;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.util.PageResultSet;

public class SearchBookVO {
    private UserDO u;
    private PageResultSet pages;
    private Integer count;

    public SearchBookVO() {
    }

    public SearchBookVO(UserDO u, PageResultSet pages, Integer count) {
        this.u = u;
        this.pages = pages;
        this.count = count;
    }

    public UserDO getU() {
        return u;
    }

    public void setU(UserDO u) {
        this.u = u;
    }

    public PageResultSet getPages() {
        return pages;
    }

    public void setPages(PageResultSet pages) {
        this.pages = pages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SearchBookVO{" +
                "u=" + u +
                ", pages=" + pages +
                ", count=" + count +
                '}';
    }
}
