package com.example.fybproject.listView.search;

public class SearchListItem {
    private String shop;
    private String surl;

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

    public SearchListItem(String shop, String surl) {
        this.shop = shop;
        this.surl = surl;
    }
}
