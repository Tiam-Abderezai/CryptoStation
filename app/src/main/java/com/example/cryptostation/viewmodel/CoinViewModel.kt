package com.example.cryptostation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptostation.data.CoinDatabase

import com.example.cryptostation.models.Coin
import com.example.cryptostation.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Coin>>
    private val repository: CoinRepository

    init {
        val coinDao = CoinDatabase.getDatabase(application).coinDao()
        repository = CoinRepository(coinDao)
        readAllData = repository.readAllData
    }

    fun addCoin(coin: Coin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoin(coin)
        }
    }

    fun deleteCoin(coin: Coin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCoin(coin)
        }
    }
}