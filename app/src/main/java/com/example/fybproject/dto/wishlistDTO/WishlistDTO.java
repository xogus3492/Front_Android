package com.example.fybproject.dto.wishlistDTO;

public class WishlistDTO {
    private int pname;
    private String purl;
    private long p_id;
    private String notes;
    private int price;
    private String pimage; // 이미지 업로드 물어보기

    public int getPname() {
        return pname;
    }

    public void setPname(int pname) {
        this.pname = pname;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
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
                ", p_id = " + p_id +
                ", notes = " + notes +
                ", price = " + price +
                ", pimage = " + pimage +
                '}';
    }
}
