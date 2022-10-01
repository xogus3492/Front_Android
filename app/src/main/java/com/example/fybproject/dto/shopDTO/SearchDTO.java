package com.example.fybproject.dto.shopDTO;

public class SearchDTO {
    private String shop;
    private String surl;

    public SearchDTO() { }

    public SearchDTO(String shop) {
        this.shop = shop;
    }

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

    @Override
    public String toString() {
        return "PostResult {" +
                "shop = " + shop +
                ", surl = " + surl +
                '}';
    }
}
