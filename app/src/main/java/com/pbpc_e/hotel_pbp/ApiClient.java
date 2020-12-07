package com.pbpc_e.hotel_pbp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://10.0.2.2:8000/"; // Link Local Host
//    public static final String BASE_URL = "https://cloud-hotel.herokuapp.com/"; // Link Hosting
    public static final String BASE_API_URL = BASE_URL + "api/";

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
