package com.example.fybproject.listView.search;

public class SearchListItem {
    private String name1;
    private String url1;
    private String name2;
    private String url2;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public SearchListItem(String name1, String url1, String name2, String url2) {
        this.name1 = name1;
        this.url1 = url1;
        this.name2 = name2;
        this.url2 = url2;
    }
}
