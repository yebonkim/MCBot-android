package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class Room {
    private String name;
    private int ownerId;
    private int roomId;

    public Room() {

    }

    public Room(String name, int ownerId, int roomId) {
        this.name = name;
        this.ownerId = ownerId;
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
