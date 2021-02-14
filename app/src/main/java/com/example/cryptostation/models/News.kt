package com.example.cryptostation.models
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class News(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)
