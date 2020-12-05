package com.example.appkp.api

import com.example.appkp.model.auth.*
import com.example.appkp.model.sensors.SensorResponse
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
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST("api/login")
    fun onLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST("api/pasien")
    fun savePhoto(
        @Field("gambar") gambar: String,
        @Header("Authorization") token: String
    ): Call<UserPhotoResponse2>


    @GET("api/logout")
    fun logout(
        @Header("Authorization") token: String
    ) : Call<LogoutResponse>


    @GET("api/find")
    fun getUser(
        @Header("Authorization") token: String
    ) : Call<FindUserResponse>


    @FormUrlEncoded
    @POST("api/pasien")
    fun updatePersonalInfo(
        @Field("nama") nama: String?,
        @Field("ttl") ttl: String?,
        @Field("jenis_kelamin") jenisKelamin: String?,
        @Field("alamat") alamat: String?,
        @Field("tinggi_badan") tinggiBadan: Int?,
        @Field("berat_badan") beratBadan: Int?,
        @Header("Authorization") token: String
    ) : Call<UserPhotoResponse2>




    /**
     * Insert Spo2 Bpm Pi
     */

    @FormUrlEncoded
    @POST("api/sensor")
    fun insertSensorData(
        @Field("bpm") bpm: String,
        @Field("spo2") spo2: String,
        @Field("pi") pi: String,
        @Header("Authorization") token: String
    ) : Call<SensorResponse>

}
