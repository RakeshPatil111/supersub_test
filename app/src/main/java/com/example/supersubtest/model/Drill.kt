package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class Drill(
    @SerializedName("difficulty")
    var difficulty: String?,
    @SerializedName("duration")
    var duration: Int?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("subtitle")
    var subtitle: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Video?
)