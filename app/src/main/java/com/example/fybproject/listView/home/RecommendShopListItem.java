package com.example.fybproject.listView.home;

public class RecommendShopListItem {
    private String rank;
    private String shop;
    private String url;
    private String simg;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSimg() {
        return simg;
    }

    public void setSimg(String simg) {
        this.simg = simg;
    }

    public RecommendShopListItem(String rank, String shop, String url, String simg) {
        this.rank = rank;
        this.shop = shop;
        this.url = url;
        this.simg = simg;
    }
}
