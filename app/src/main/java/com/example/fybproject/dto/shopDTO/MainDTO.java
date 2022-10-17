package com.example.fybproject.dto.shopDTO;

public class MainDTO {
    private String email;
    private String name;
    private String profileImagePath;
    private char gender;
    private int height;
    private int weight;
    private int age;
    private String shop;
    private String surl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
                "email = " + email +
                ", name = " + name +
                ", gender = " + gender +
                ", height = " + height +
                ", weight = " + weight +
                ", age = " + age +
                ", shop = " + shop +
                ", surl = " + surl +
                '}';
    }
}
