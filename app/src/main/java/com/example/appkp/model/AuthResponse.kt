package com.example.appkp.model


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)
