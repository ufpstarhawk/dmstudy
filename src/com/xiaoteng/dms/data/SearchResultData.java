package com.xiaoteng.dms.data;

public class SearchResultData {
    private String TextTitleMyMsg;
    private String TextDownloadMyMsg;
    private String TextDateTimeMyMsg;

    public void SearchResultData() {

    }

    public void SearchResultData(String TextTitleMyMsg, String TextDownloadMyMsg, String TextDateTimeMyMsg) {
        this.TextTitleMyMsg = TextTitleMyMsg;
        this.TextDownloadMyMsg = TextDownloadMyMsg;
        this.TextDateTimeMyMsg = TextDateTimeMyMsg;
    }

    public String getTextTitleMyMsg(){
        return this.TextTitleMyMsg;
    }
    public void setTextTitleMyMsg(String textTitleMyMsg){
        this.TextTitleMyMsg=textTitleMyMsg;
    }
    public String getTextDateTimeMyMsg(){
        return this.TextDateTimeMyMsg;
    }
    public void setTextDateTimeMyMsg(String textDateTimeMyMsg){
        this.TextDateTimeMyMsg=textDateTimeMyMsg;
    }

    public String getTextDownloadMyMsg(){
        return this.TextDownloadMyMsg;
    }
}
