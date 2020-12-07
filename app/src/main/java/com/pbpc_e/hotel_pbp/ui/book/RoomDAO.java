package com.pbpc_e.hotel_pbp.ui.book;

import android.util.Base64;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;
import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.R;

public class RoomDAO {

    @SerializedName("id")
    private int id;

    @SerializedName("room_type")
    private String type;

    @SerializedName("desc")
    private String desc;

    @SerializedName("facilities")
    private String facilities;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("available_room")
    private int availableRoom;

    @SerializedName("price_per_night")
    private double pricePerNight;

    @SerializedName("image64")
    private String image;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getFacilities() {
        return facilities;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableRoom() {
        return availableRoom;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getImage() {
        return image;
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imgURL) {
        if (imgURL != null) {
            try{
                byte[] imageByteArray = Base64.decode(imgURL, Base64.DEFAULT);
                Glide.with(imageView)
                        .load(imageByteArray)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.itemdefault)
                        .into(imageView);
            }catch (Exception e){

            }
        }
    }
}
