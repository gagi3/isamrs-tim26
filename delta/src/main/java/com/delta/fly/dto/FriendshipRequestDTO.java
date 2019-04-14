package com.delta.fly.dto;

public class FriendshipRequestDTO {

    private Long fromID;
    private Long toID;
    private Boolean accept;
    private Boolean delete;

    public FriendshipRequestDTO() {
    }

    public Long getFromID() {
        return fromID;
    }

    public void setFromID(Long fromID) {
        this.fromID = fromID;
    }

    public Long getToID() {
        return toID;
    }

    public void setToID(Long toID) {
        this.toID = toID;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
