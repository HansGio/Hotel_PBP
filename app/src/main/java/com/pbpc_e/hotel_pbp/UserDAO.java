package com.pbpc_e.hotel_pbp;

import com.google.gson.annotations.SerializedName;

public class UserDAO {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("image_path")
    private String image;

    @SerializedName("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
