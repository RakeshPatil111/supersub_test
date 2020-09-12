package com.example.supersubtest.model


import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Explore(
    @SerializedName("banner")
    var banner: Banner?,
    @SerializedName("categories")
    var categories: List<Category>?,
    @SerializedName("topPicks")
    var topPicks: TopPicks?
) : Serializable