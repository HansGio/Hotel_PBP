package com.pbpc_e.hotel_pbp.ui.book;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;


public class Kamar {
    public String namaKamar;
    public String deskripsiKamar;
    public String fasilitasKamar;
    public double hargaKamar;
    public String imgURL;

    public Kamar(String namaKamar, String deskripsiKamar, String fasilitasKamar, double hargaKamar, String imgURL) {
        this.namaKamar = namaKamar;
        this.deskripsiKamar = deskripsiKamar;
        this.fasilitasKamar = fasilitasKamar;
        this.hargaKamar = hargaKamar;
        this.imgURL = imgURL;
    }

    public String getNamaKamar() {
        return namaKamar;
    }

    public void setNamaKamar(String npm) {
        this.namaKamar = namaKamar;
    }

    public String getDeskripsiKamar() {
        return deskripsiKamar;
    }

    public void setDeskripsiKamar(String deskripsiKamar) {
        this.deskripsiKamar = deskripsiKamar;
    }

    public String getFasilitasKamar() {
        return fasilitasKamar;
    }

    public void setFasilitasKamar(String fasilitasKamar) {
        this.fasilitasKamar = fasilitasKamar;
    }

    public double getHargaKamar() {
        return hargaKamar;
    }

    public void setHargaKamar(double hargaKamar) {
        this.hargaKamar = hargaKamar;
    }

    public String getStringHargaKamar() {
        return String.valueOf(hargaKamar);
    }

    public void setStringHargaKamar(String hargaKamar) {
        if(!hargaKamar.isEmpty()) {
            this.hargaKamar = Double.parseDouble(hargaKamar);
        } else {
            this.hargaKamar = 0;
        }
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }


    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imgURL) {
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }
}
