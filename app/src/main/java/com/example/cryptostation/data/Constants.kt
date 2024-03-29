package com.example.cryptostation.data

class Constants {

    companion object {
        val fiatArrayName = arrayOf("USD","EUR","GBP")
        val fiatArraySymbol = arrayOf("$","€","£")
        val priceArrayDuration = arrayOf("1h","24h","7d","14d","30d","200d","1y")
        val coinArray = arrayOf("BTC","ETH","XTZ")
//        const val URL_EXCHANGE_USD = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=1h"
        const val URL_NEWS = "https://cryptopanic.com/api/v1/posts/?auth_token="
        const val TOKEN_NEWS = "ea147fc85fc2b5af3e7561e60cf49363f8e6a683"
    }

    enum class TimeScale(val history: Int) {
        HOUR_1(60),
        HOUR_24(24),
        DAYS_7(7),
        DAYS_14(14),
        DAYS_30(30),
        DAYS_200(200)
    }
}