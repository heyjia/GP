package com.heihei.bookrecommendsystem.util;

import java.util.ArrayList;
import java.util.List;

public class PageResultSet {
    private int page;

    private int limit;

    private List<?> dataList = new ArrayList<>();

    private long count = 0;

    public PageResultSet() {
    }

    public PageResultSet(int page, int limit, List<?> dataList, long count) {
        this.page = page;
        this.limit = limit;
        this.dataList = dataList;
        this.count = count;
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

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageResultSet{" +
                "page=" + page +
                ", limit=" + limit +
                ", dataList=" + dataList +
                ", count=" + count +
                '}';
    }
}
