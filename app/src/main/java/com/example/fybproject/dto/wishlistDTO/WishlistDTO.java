package com.example.fybproject.dto.wishlistDTO;

public class WishlistDTO {
    private String pname;
    private String purl;
    private long pid;
    private String notes;
    private int price;
    private String pimage;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    @Override
    public String toString() {
        return "PostResult {" +
                "pname = " + pname +
                ", purl = " + purl +
                ", pid = " + pid +
                ", notes = " + notes +
                ", price = " + price +
                ", pimage = " + pimage +
                '}';
    }
}
