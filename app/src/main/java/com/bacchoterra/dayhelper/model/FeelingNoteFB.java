package com.bacchoterra.dayhelper.model;

public class FeelingNoteFB extends FeelingNote {

    private String userId;
    private String postId;

    public FeelingNoteFB() {
    }

    public FeelingNoteFB(FeelingNote baseNote,String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
        this.userName = baseNote.getUserName();
        this.feeling = baseNote.getFeeling();
        this.title = baseNote.getTitle();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
