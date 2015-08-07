package com.xiaoteng.dms.data;

public class SearchHistoryData {
    private String id ="";
    private String content = "";
    public String getId() {
        return id;
    }
    public SearchHistoryData setId(String id) {
        this.id = id;
        return this;
    }
    public String getContent() {
        return content;
    }
    public SearchHistoryData setContent(String content) {
        this.content = content;
        return this;
    }
}