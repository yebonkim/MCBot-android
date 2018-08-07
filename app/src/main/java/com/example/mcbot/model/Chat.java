package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class Chat {
    private String message;
    private String username;

    public Chat() {

    }

    public Chat(String message, String username) {
        this.message = message;
        this.username = username;
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
}
