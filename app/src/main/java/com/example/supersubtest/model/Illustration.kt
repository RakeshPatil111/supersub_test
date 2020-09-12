package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName

data class Illustration(
    @SerializedName("caption")
    var caption: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("imageUrl")
    var imageUrl: String?
)