package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class TopPicks(
    @SerializedName("drills")
    var drills: List<Drill>?,
    @SerializedName("title")
    var title: String?
)