package com.example.fybproject.dto.myClosetDTO;

public class ClosetAddDTO {
    private long id;
    private String pname;
    private String pnotes;
    private String pkind;
    private String  status;
    private String statusMessage;

    public ClosetAddDTO(String pname, String pnotes, String pkind) {
        this.pname = pname;
        this.pnotes = pnotes;
        this.pkind = pkind;
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

    public String getPnotes() {
        return pnotes;
    }

    public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
    }

    public String getPkind() {
        return pkind;
    }

    public void setPkind(String pkind) {
        this.pkind = pkind;
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
                "id = " + id +
                ", status = " + status +
                ", statusMessage = " + statusMessage +
                '}';
    }
}
