package com.example.fybproject.dto.wishlistDTO;

public class WishAddDTO {
    private int pname;
    private String notes;
    private String purl;
    private int price;
    private String pimage; // 이미지 업로드 물어보기
    private int status;
    private String statusMessage;

    public WishAddDTO(int pname, String notes, String purl, int price, String pimage) {
        this.pname = pname;
        this.notes = notes;
        this.purl = purl;
        this.price = price;
        this.pimage = pimage;
    }

    public int getPname() {
        return pname;
    }

    public void setPname(int pname) {
        this.pname = pname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
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

    @Override
    public String toString() {
        return "PostResult {" +
                "status = " + status +
                ", statusMessage = " + statusMessage +
                '}';
    }
}
