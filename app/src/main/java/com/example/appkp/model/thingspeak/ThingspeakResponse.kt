package com.example.appkp.model.thingspeak


import com.google.gson.annotations.SerializedName

data class ThingspeakResponse(
    @SerializedName("channel")
    val channel: Channel,
    @SerializedName("feeds")
    val feeds: List<Feed>
)
