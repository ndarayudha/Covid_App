package com.example.appkp.api

import com.example.appkp.model.auth.AuthResponse
import com.example.appkp.model.auth.LogoutResponse
import com.example.appkp.model.auth.UserPhotoResponse
import com.example.appkp.model.thingspeak.ThingspeakResponse
import com.example.appkp.util.Constant
import retrofit2.Call
import retrofit2.http.*

interface API {

    /**
     * Handle Authentication request
     */
    @FormUrlEncoded
    @POST("api/register")
    fun onRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST("api/login")
    fun onLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST("api/save_user_photo")
    fun savePhoto(
        @Field("photo") photo: String,
        @Header("Authorization") token: String
    ): Call<UserPhotoResponse>


    @GET("api/logout")
    fun logout(
        @Header("Authorization") token: String
    ) : Call<LogoutResponse>



    /**
     * Handle thingspeak request
     */
    @GET("channels/1081662/feeds.json?api_key=${Constant.API_KEY_THINGSPEAK}&results")
    fun getThingspeakData(
        @Query("results")result: Int
    ): Call<ThingspeakResponse>
}
