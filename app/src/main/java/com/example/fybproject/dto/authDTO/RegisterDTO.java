package com.example.fybproject.dto.authDTO;

public class RegisterDTO {
    private String email;
    private String pw;
    private String name;
    private String gender;
    private int height;
    private int weight;
    private int age;
    private int status;
    private String statusMessage;

    public RegisterDTO(String email, String pw, String name, String gender, int height, int weight, int age) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }


    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "PostResult {" +
                "status = " + status +
                ", statusMessage = " + statusMessage +
                '}';
    }
}
