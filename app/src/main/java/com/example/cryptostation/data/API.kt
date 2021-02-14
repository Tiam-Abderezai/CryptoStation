package com.example.cryptostation.data

import com.example.cryptostation.models.Coin
//import com.example.cryptostation.models.News
import com.google.gson.JsonObject
//import com.example.cryptostation.model.CoinItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

//    @GET(Constants.API_URL + Constants.API_TOKEN +"&{?}")
//    fun getCryptoNews(@Query("currencies") coinSymbol: String): Call<Coin>

    @GET("https://api.coingecko.com/api/v3/coins/markets?vs_currency=&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=")
    fun getAllCryptoData(
        @Query("vs_currency") FIAT: String,
        @Query("price_change_percentage") DURATION: String
    ): Call<List<Coin>>

    @GET("https://api.coingecko.com/api/v3/coins/{id}/market_chart/range?vs_currency=usd&from=1611216574&to=1611821374")
    fun getACryptoData(
        @Path("id") id: String
//        @Query("price_change_percentage") DURATION: String
    ): Call<JsonObject>

    @GET("?auth_token=ea147fc85fc2b5af3e7561e60cf49363f8e6a683")
    fun getCryptoNews(
        @Query("currencies") currencies: String,
        @Query("title") title: String,
        @Query("public") public: Boolean,
        @Query("regions") regions: String
    ): Call<JsonObject>
}