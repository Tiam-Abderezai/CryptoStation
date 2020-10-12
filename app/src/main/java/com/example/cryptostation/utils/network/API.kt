package com.example.cryptostation.utils.network

import com.example.cryptostation.models.Coin
//import com.example.cryptostation.model.CoinItem
import retrofit2.Call
import retrofit2.http.GET

interface API {

//    @GET(Constants.API_URL + Constants.API_TOKEN +"&{?}")
//    fun getCryptoNews(@Query("currencies") coinSymbol: String): Call<Coin>

    @GET(Constants.URL_EXCHANGE)
    fun getCryptoData(): Call<List<Coin>>
}