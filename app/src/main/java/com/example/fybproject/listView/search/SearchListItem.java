package com.example.fybproject.listView.search;

public class SearchListItem {
    private String shop;
    private String surl;
    private String simg;

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getSimg() {
        return simg;
    }

    public void setSimg(String simg) {
        this.simg = simg;
    }

    public SearchListItem(String shop, String surl, String simg) {
        this.shop = shop;
        this.surl = surl;
        this.simg = simg;
    }
}
