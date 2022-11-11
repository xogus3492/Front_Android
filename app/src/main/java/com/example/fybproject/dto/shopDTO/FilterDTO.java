package com.example.fybproject.dto.shopDTO;

import com.google.gson.annotations.SerializedName;

public class FilterDTO {
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
}
