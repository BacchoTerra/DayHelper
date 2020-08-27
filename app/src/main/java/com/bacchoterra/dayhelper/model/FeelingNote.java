package com.bacchoterra.dayhelper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "feeling_note_table")
public class FeelingNote implements Serializable {

    public static final int HAPPY = 0;
    public static final int SAD = 1;
    public static final int FEAR = 2;
    public static final int LOVE = 3;
    public static final int ANGER =4;

    String userName;
    String title;
    private String note;
    int feeling;
    @PrimaryKey(autoGenerate = true)
    private int roomId;
    private Boolean isPublished;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }
}
