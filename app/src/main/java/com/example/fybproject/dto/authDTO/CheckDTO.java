package com.example.fybproject.dto.authDTO;

public class CheckDTO {
    private String pnum;
    private String randNum;
    private String status;
    private String statusMessage;

    public CheckDTO(String pnum) {
        this.pnum = pnum;
    }

    public String getpNum() {
        return pnum;
    }

    public void setpNum(String pnum) {
        this.pnum = pnum;
    }

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
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
                "randNum = " + randNum +
                ", status = " + status +
                ", statusMessage = " + statusMessage +
                '}';
    }
}
