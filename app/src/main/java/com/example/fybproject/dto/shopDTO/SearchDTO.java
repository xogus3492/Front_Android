package com.example.fybproject.dto.shopDTO;

public class SearchDTO {
    private String shop;
    private String surl;
    private String simg;

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

    public String getSimg() {
        return simg;
    }

    public void setSimg(String simg) {
        this.simg = simg;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "shop = " + shop +
                ", surl = " + surl +
                ", smig = " + simg +
                '}';
    }
}
