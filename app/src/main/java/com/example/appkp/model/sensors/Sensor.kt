package com.example.appkp.model.sensors


import com.google.gson.annotations.SerializedName

data class Sensor(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("berat_badan")
    val beratBadan: Int,
    @SerializedName("bpm")
    val bpm: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("gambar")
    val gambar: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jenis_kelamin")
    val jenisKelamin: String,
    @SerializedName("kondisi")
    val kondisi: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pi")
    val pi: String,
    @SerializedName("spo2")
    val spo2: String,
    @SerializedName("tinggi_badan")
    val tinggiBadan: Int,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("usia")
    val usia: Int
)
