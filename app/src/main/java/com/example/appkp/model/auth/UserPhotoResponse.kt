package com.example.appkp.model.auth


import com.google.gson.annotations.SerializedName

data class UserPhotoResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("success")
    val success: Boolean
)
