package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class User {
    private String title;
    private String password;
    private String username;
    private String profileName;
    private int bot;

    public User() {

    }

    public User(String title, String password, String username, String profileName, int bot) {
        this.title = title;
        this.password = password;
        this.username = username;
        this.profileName = profileName;
        this.bot = bot;
    }

    public int getBot() {
        return bot;
    }

    public void setBot(int bot) {
        this.bot = bot;
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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
