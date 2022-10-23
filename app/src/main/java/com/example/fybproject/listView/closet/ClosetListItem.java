package com.example.fybproject.listView.closet;

public class ClosetListItem {
    private Long uid;
    private String pname;
    private String pnote;
    private String pkind;

    public ClosetListItem(long uid, String pname, String pnote, String pkind) {
        this.uid = uid;
        this.pname = pname;
        this.pnote = pnote;
        this.pkind = pkind;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPnote() {
        return pnote;
    }

    public void setPnote(String pnote) {
        this.pnote = pnote;
    }

    public String getPkind() {
        return pkind;
    }

    public void setPkind(String pkind) {
        this.pkind = pkind;
    }
}
