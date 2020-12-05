package com.example.appkp.model.sensors

data class Sensor(
    val bpm: String,
    val created_at: String,
    val id: Int,
    val id_pasien: Int,
    val latitude: Any,
    val longitude: Any,
    val pi: String,
    val spo2: String,
    val updated_at: String
)