package com.example.cryptostation.models
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Parcelize

data class News(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
//    @SerializedName("source")
//    val source: String
//    val results: List<String>?,
//    val sources: List<String>?
)
//    : Parcelable

//@Parcelize
//data class Results(
////    val id: String = "",
//    @SerializedName("title")
//    val title: String,
//
////    val source: Source = Source(),
////
////    val domain: String = "",
////
////    val created_at: String = "",
////
////    val slug: String = "",
//    @SerializedName("url")
//    val url: String,
//
//    @SerializedName("source")
//    val source: Source
//
////    val published_at: String = ""
//) : Parcelable
//
//@Parcelize
//data class Source(val title: String = "", val domain: String = "") : Parcelable