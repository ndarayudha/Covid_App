package com.example.appkp.model.thingspeak


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("entry_id")
    val entryId: Int,
    @SerializedName("field1")
    val field1: String,
    @SerializedName("field2")
    val field2: String,
    @SerializedName("field3")
    val field3: String,
    @SerializedName("field4")
    val field4: String,
    @SerializedName("field5")
    val field5: String,
    @SerializedName("field6")
    val field6: String
)
