package com.example.cryptostation.adapters

import android.graphics.RectF
import com.example.cryptostation.models.Coin
import com.robinhood.spark.SparkAdapter

class ChartAdapter(private val dailyData: List<Coin>) : SparkAdapter() {
    override fun getCount() = dailyData.size

    override fun getItem(index: Int) = dailyData[index]

    override fun getY(index: Int): Float {
        var chosenDayData = 0.0F
//        if (dailyData[index].currentPrice .rates?.keys!!.contains("EUR")) {
            chosenDayData = dailyData[index].currentPrice.toFloat()
//        }
        return chosenDayData!!
    }

}