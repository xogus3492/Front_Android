package com.example.fybproject.dto.myClosetDTO;

public class ClosetAddDTO {
    private String pname;
    private String pnotes;
    private int pkind;
    private int status;
    private String statusMessage;

    public ClosetAddDTO(String pname, String pnotes, int pkind) {
        this.pname = pname;
        this.pnotes = pnotes;
        this.pkind = pkind;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPnotes() {
        return pnotes;
    }

    public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
    }

    public int getPkind() {
        return pkind;
    }

    public void setPkind(int pkind) {
        this.pkind = pkind;
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
