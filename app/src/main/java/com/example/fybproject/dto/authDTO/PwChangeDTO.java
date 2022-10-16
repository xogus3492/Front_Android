package com.example.fybproject.dto.authDTO;

public class PwChangeDTO {
    private String email;
    private String pw;
    private String newPw;
    private String status;
    private String statusMessage;

    public PwChangeDTO(String email, String pw, String newPw) {
        this.email = email;
        this.pw = pw;
        this.newPw = newPw;
    } // 비밀번호 변경

    public PwChangeDTO(String email, String newPw) {
        this.email = email;
        this.newPw = newPw;
    } // 비밀번호 잃어버렸을 때 변경

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

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "status = " + status +
                ", statusMessage = " + statusMessage +
                '}';
    }
}
