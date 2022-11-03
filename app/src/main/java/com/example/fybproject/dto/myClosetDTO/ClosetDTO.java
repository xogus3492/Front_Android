package com.example.fybproject.dto.myClosetDTO;

public class ClosetDTO {
    private long id;
    private String pname;
    private String pnotes;
    private String pkind;
    private String closetImagePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPnotes() {
        return pnotes;
    }

    public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPkind() {
        return pkind;
    }

    public void setPkind(String pkind) {
        this.pkind = pkind;
    }

    public String getClosetImagePath() {
        return closetImagePath;
    }

    public void setClosetImagePath(String closetImagePath) {
        this.closetImagePath = closetImagePath;
    }

    @Override
    public String toString() {
        return "PostResult {" +
                "id = " + id +
                ", pname = " + pname +
                ", pnotes = " + pnotes +
                ", pkind = " + pkind +
                ", closetImagePath = " + closetImagePath +
                '}';
    }
}
