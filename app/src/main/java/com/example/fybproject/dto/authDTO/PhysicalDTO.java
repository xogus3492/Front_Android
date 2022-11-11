package com.example.fybproject.dto.authDTO;

public class PhysicalDTO {
    private String userData;

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    @Override
    public String toString() {
        return "PhysicalDTO{" +
                "userData=" + userData +
                '}';
    }
}
