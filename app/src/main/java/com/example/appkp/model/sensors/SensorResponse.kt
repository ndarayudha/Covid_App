package com.example.appkp.model.sensors

data class SensorResponse(
    val message: String,
    val sensor: Sensor,
    val success: Boolean
)