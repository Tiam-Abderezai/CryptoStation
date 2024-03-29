package com.example.cryptostation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptostation.models.Coin

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getDatabase(context: Context): CoinDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinDatabase::class.java,
                    "coin_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}