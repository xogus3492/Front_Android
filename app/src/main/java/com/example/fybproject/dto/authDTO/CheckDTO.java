package com.example.fybproject.dto.authDTO;

public class CheckDTO {
    private String pNum;
    private String randNum;

    public CheckDTO(String pNum) {
        this.pNum = pNum;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "randNum = " + randNum +
                '}';
    }
}
