package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 11..
 */

public class ToDoUser {
    private String toDoName;
    private String username;

    public ToDoUser() {

    }

    public ToDoUser(String toDoName, String username) {
        this.username = username;
        this.toDoName = toDoName;
    }

    public String getToDoName() {
        return toDoName;
    }

    public void setToDoName(String toDoName) {
        this.toDoName = toDoName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
