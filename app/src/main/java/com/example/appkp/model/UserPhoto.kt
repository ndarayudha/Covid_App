package com.example.appkp.model


import com.google.gson.annotations.SerializedName

data class UserPhoto(
    @SerializedName("photo")
    val photo: String,
    @SerializedName("success")
    val success: Boolean
)
