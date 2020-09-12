package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("_id")
    var id: String?,
    @SerializedName("url")
    var url: String?
)