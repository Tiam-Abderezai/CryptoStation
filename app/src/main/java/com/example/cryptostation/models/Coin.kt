package com.example.cryptostation.models

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    val id: String,
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

)
