package com.pbpc_e.hotel_pbp.ui.book;

import com.google.gson.annotations.SerializedName;
import com.pbpc_e.hotel_pbp.UserDAO;

import java.util.List;

public class RoomResponse {

    @SerializedName("room")
    private RoomDAO room = null;

    @SerializedName("rooms")
    private List<RoomDAO> rooms = null;

    @SerializedName("message")
    private String message;

    public RoomDAO getRoom() {
        return room;
    }

    public List<RoomDAO> getRooms() {
        return rooms;
    }

    public String getMessage() {
        return message;
    }
}
