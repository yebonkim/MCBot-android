package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 11..
 */

public class ToDo {
    private String toDo;
    private String toDoName;
    private boolean isWarned;
    private boolean isDone;
    private long deadline;

    public ToDo() {

    }

    public ToDo(String toDo, String toDoName, boolean isWarned, boolean isDone, long deadline) {
        this.toDo = toDo;
        this.toDoName = toDoName;
        this.isWarned = isWarned;
        this.isDone = isDone;
        this.deadline = deadline;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public String getToDoName() {
        return toDoName;
    }

    public void setToDoName(String toDoName) {
        this.toDoName = toDoName;
    }

    public boolean isWarned() {
        return isWarned;
    }

    public void setWarned(boolean warned) {
        isWarned = warned;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
