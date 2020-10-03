package com.example.appkp.model.sensors


import com.google.gson.annotations.SerializedName

data class SensorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("sensor")
    val sensor: Sensor,
    @SerializedName("success")
    val success: Boolean
)
