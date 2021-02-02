package com.example.cryptostation.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "coin_table")
data class Coin(
    @PrimaryKey(autoGenerate = true)
    val starId: Int,
    @SerializedName("id")
    val coinId: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("price_change_24h")
    val price_change_24h: Double,
    @SerializedName("price_change_percentage_1h_in_currency")
    val price_change_percentage_1h_in_currency: Double,
    @SerializedName("price_change_percentage_24h_in_currency")
    val price_change_percentage_24h_in_currency: Double,
    val fiat_symbol: String

) : Parcelable
