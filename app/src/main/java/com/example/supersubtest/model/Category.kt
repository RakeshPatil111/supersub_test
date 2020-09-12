package com.example.supersubtest.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("icon")
    var icon: String?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("name")
    var name: String?
) : Serializable