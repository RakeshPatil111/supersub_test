package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("category")
    var category: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("difficulty")
    var difficulty: String?,
    @SerializedName("duration")
    var duration: Int?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("illustration")
    var illustration: Illustration?,
    @SerializedName("reps")
    var reps: Int?,
    @SerializedName("sets")
    var sets: Int?,
    @SerializedName("subtitle")
    var subtitle: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Video
)