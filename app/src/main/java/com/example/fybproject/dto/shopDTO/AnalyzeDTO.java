package com.example.fybproject.dto.shopDTO;

public class AnalyzeDTO {
    private long sid;
    private char gender;
    private int age;
    private String surl;
    private String redirect_url;
    private String status;

    public AnalyzeDTO(long sid, char gender, int age, String surl) {
        this.sid = sid;
        this.gender = gender;
        this.age = age;
        this.surl = surl;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "redirect_url = " + redirect_url +
                ", status = " + status +
                '}';
    }
}
