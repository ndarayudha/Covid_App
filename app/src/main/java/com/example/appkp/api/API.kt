package com.example.appkp.api

import com.example.appkp.model.UserPhoto
import com.example.appkp.model.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface API {


    @FormUrlEncoded
    @POST("api/register")
    fun onRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ) : Call<UserResponse>


    @FormUrlEncoded
    @POST("api/login")
    fun onLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<UserResponse>


    @FormUrlEncoded
    @POST("save_user_photo")
    fun savePhoto(
        @Field("photo") photo : String,
        @Header("Authorization") token: String
    ) : Call<UserPhoto>
}
