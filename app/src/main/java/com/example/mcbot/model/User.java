package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class User {
    private String title;
    private String password;
    private String username;
    private String profile;

    public User() {

    }

    public User(String title, String password, String username, String profile) {
        this.title = title;
        this.password = password;
        this.username = username;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShowingName() {
        return username + " (" + title + ")";
    }
}
