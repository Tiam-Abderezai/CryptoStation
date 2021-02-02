package com.example.cryptostation.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptostation.models.Coin

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCoin(coin: Coin)

    @Delete
    suspend fun deleteCoin (coin: Coin)

    @Query("SELECT * FROM coin_table ORDER BY starId ASC")
    fun readAllCoins(): LiveData<List<Coin>>

}