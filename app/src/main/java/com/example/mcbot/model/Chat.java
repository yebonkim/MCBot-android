package com.example.mcbot.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class Chat {
    private String message;
    private String username;
    private int unreadCnt;
    private long timestamp;

    public Chat() {

    }

    public Chat(String message, String username, int unreadCnt, long timestamp) {
        this.message = message;
        this.username = username;
        this.unreadCnt = unreadCnt;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(int unreadCnt) {
        this.unreadCnt = unreadCnt;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
