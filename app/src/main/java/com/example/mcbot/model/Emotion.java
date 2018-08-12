package com.example.mcbot.model;

public class Emotion {
    private boolean bool;
    private String username;

    public Emotion() {

    }

    public Emotion(boolean bool, String username) {
        this.bool = bool;
        this.username = username;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
