package com.liferay.sample;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecordsResponse {
    // Define properties that match the JSON structure from the Express endpoint
    @JsonProperty("items")
    private List<Item> items;
    private int totalCount;

    private int pageSize;
    private int page;

    public List<Item> getRecords() {
        return items;
    }

    public void setRecords(List<Item> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
