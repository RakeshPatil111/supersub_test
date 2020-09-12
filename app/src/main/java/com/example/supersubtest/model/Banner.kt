package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("header")
    var header: String?,
    @SerializedName("image")
    var image: String?
)