package com.pbpc_e.hotel_pbp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("user")
    Call<UserResponse> getAllUser(@Query("data") String data);

    @GET("user/{id}")
    Call<UserResponse> getUserById(@Path("id") String id,
                                   @Query("data") String data);

    @POST("user/update/{id}")
    @FormUrlEncoded
    Call<UserResponse> updateUser(@Path("id") String id,
                                  @Field("nama") String nama,
                                  @Field("nim") String nim,
                                  @Field("prodi") String prodi,
                                  @Field("fakultas") String fakultas,
                                  @Field("jenis_kelamin") String jenis_kelamin,
                                  @Field("password") String password);

    @POST("user/delete/{id}")
    Call<UserResponse> deleteUser(@Path("id") String id);


    @POST("login")
    @FormUrlEncoded
    Call<UserResponse> login(@Field("email") String email,
                             @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<UserResponse> register(@Field("name") String name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("phone") String phone);

    @GET("details")
    Call<UserResponse> details(@Header("Authorization") String authHeader);

    @POST("update")
    @FormUrlEncoded
    Call<UserResponse> update(@Header("Authorization") String authHeader,
                              @Field("name") String name,
                              @Field("email") String email,
                              @Field("phone") String phone,
                              @Field("image64") String image64);

    @POST("uploadImg")
    @FormUrlEncoded
    Call<UserResponse> uploadImage(@Header("Authorization") String authHeader,
                                   @Field("image64") String image64);
}
