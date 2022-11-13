package com.example.fybproject.dto.authDTO;

public class SocialUrlDTO {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "url = " + url +
                '}';
    }
}
