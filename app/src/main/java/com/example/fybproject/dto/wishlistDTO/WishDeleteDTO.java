package com.example.fybproject.dto.wishlistDTO;

public class WishDeleteDTO {
    private long pid;
    private String status;
    private String statusMessage;

    public WishDeleteDTO(long pid) {
        this.pid = pid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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
