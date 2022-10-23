package com.example.fybproject.dto.wishlistDTO;

public class WishUpdateDTO {
    private long pid;
    private String pname;
    private String notes;
    private String purl;
    private int price;
    //private String pimage;
    private String status;
    private String statusMessage;

    public WishUpdateDTO(long pid, String pname, String notes, String purl, int price) {
        this.pid = pid;
        this.pname = pname;
        this.notes = notes;
        this.purl = purl;
        this.price = price;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
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
