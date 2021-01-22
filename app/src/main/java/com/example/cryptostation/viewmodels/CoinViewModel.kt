package com.example.cryptostation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.models.Coin

class CoinViewModel : ViewModel() {

    private val mCoinsLiveData: MutableLiveData<MutableList<Coin>>


    // Variable for saving the RecyclerView position state of the coins
    init {
        mCoinsLiveData = MutableLiveData()
//        mCoinsLiveData.value = createCoins()
        }


    fun getCoins(): LiveData<MutableList<Coin>> {
        return mCoinsLiveData
    }


//    private fun createCoins(): MutableList<Coin> {
//        val coins = mutableListOf<Coin>()
//        for (i in 1..coins.size)
//        println("VIEWMODEL: " + coins[i])
//        return coins
//    }

}