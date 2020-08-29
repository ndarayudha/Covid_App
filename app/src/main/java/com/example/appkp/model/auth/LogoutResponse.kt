package com.example.appkp.model.auth


import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
