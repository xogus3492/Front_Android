package com.example.fybproject.dto.shopDTO;

import com.google.gson.annotations.SerializedName;

public class FilterDTO {
    @SerializedName("value")
    private int age;

    @SerializedName("value")
    private char gender;

    private String shop;
    private String surl;

    public FilterDTO(int age){
        this.age = age;
    }

    public FilterDTO(char gender){
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
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
}
