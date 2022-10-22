package com.example.fybproject.listView.cart;

public class CartListItem {
    private Long pid;
    private String pname;
    private String notes;
    private int price;
    private String pUrl;

    public CartListItem(long pid, String pname, String notes, int price, String pUrl) {
        this.pid = pid;
        this.pname = pname;
        this.notes = notes;
        this.price = price;
        this.pUrl = pUrl;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }
}
