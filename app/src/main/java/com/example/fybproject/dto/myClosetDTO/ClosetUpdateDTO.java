package com.example.fybproject.dto.myClosetDTO;

public class ClosetUpdateDTO {
    private long id;
    private String pname;
    private String pkind;
    private String pnotes;
    private int status;
    private String statusMessage;

    public ClosetUpdateDTO(long id, String pname, String pkind, String pnotes) {
        this.id = id;
        this.pname = pname;
        this.pkind = pkind;
        this.pnotes = pnotes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPnotes() {
        return pnotes;
    }

    public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
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
