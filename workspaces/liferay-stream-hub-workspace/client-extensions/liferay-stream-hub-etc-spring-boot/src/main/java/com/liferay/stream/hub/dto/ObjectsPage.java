package com.liferay.stream.hub.dto;

import java.io.Serializable;
import java.util.List;

public class ObjectsPage<T> implements Serializable {

    private List<T> items;
    private int page;
    private int pageSize;
    private long totalCount;

    public ObjectsPage() {}

    public ObjectsPage(List<T> items, int page, int pageSize, long totalCount) {
        this.items = items;
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
