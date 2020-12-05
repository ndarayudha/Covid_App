package com.example.appkp.model.auth

data class GetUser(
    val alamat: String,
    val berat_badan: Int,
    val created_at: String,
    val gambar: String,
    val id: Int,
    val id_user: Int,
    val jenis_kelamin: String,
    val kondisi: Any,
    val lokasi: Any,
    val tinggi_badan: Int,
    val ttl: String,
    val updated_at: String,
    val usia: Int
)