package com.example.fybproject.dto.infoDTO;

public class InfoDTO {
    private String email;
    private String name;
    private String gender;
    private int age;
    private int weight;
    private int height;
    private String profileImagePath;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "email = " + email +
                ", name = " + name +
                ", gender = " + gender +
                ", age = " + age +
                ", weight = " + weight +
                ", height = " + height +
                ", profileImagePath = " + profileImagePath +
                '}';
    }
}
