package com.example.fybproject.listView.closet;

public class ClosetListItem {
    private Long uid;
    private String pname;
    private String pnote;
    private String pkind;
    private String url;

    public ClosetListItem(long uid, String pname, String pnote, String pkind, String url) {
        this.uid = uid;
        this.pname = pname;
        this.pnote = pnote;
        this.pkind = pkind;
        this.url = url;
    }

    public Long getUid() { return uid; }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
