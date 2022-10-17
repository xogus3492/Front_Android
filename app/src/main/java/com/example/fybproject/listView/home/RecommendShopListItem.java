package com.example.fybproject.listView.home;

public class RecommendShopListItem {
    private String rank;
    private String shop;
    private String url;

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

    public RecommendShopListItem(String rank, String shop, String url) {
        this.rank = rank;
        this.shop = shop;
        this.url = url;
    }
}
