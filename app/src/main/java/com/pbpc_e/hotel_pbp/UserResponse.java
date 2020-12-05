package com.pbpc_e.hotel_pbp;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    private UserDAO user = null;

    @SerializedName("message")
    private String message;

    @SerializedName("access_token")
    private String accessToken;

    public UserDAO getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
