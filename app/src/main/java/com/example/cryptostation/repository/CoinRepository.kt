package com.example.cryptostation.repository

import androidx.lifecycle.LiveData
import com.example.cryptostation.data.CoinDao
import com.example.cryptostation.models.Coin

class CoinRepository(private val coinDao: CoinDao) {
    val readAllData: LiveData<List<Coin>> = coinDao.readAllCoins()
    suspend fun addCoin(coin: Coin) {
        coinDao.addCoin(coin)
    }

    suspend fun deleteCoin(coin: Coin) {
        coinDao.deleteCoin(coin)
    }
}