package com.example.cryptostation.adapters

import android.graphics.RectF
import com.example.cryptostation.models.Coin
import com.robinhood.spark.SparkAdapter
import kotlin.math.nextTowards
import kotlin.math.withSign


// ChartAdapter used to display the Sparkline graph depicting Date (X) price (Y) axises inside DetailActivity
// It receives this data from DetailActivity inside callCoin() method after receiving coin data using Retrofit2
//                    var chartArray = ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>()
class ChartAdapter(private val chartArray: ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>) :
    SparkAdapter() {
    override fun getCount() = chartArray.size
//    var chartArray = ArrayList<Array<LongArray>>()

    override fun getItem(index: Int) = chartArray[index]

    // Returns Price as Y axis in the chart
    override fun getY(index: Int): Float {
        // Extracts the price from the map store as value
        // Uses regex to clean "[" and "]" then casts to float
        // Then returns and displays price on the chart
        val priceData = chartArray.get(index)
            .values.toString()
            .replace("[","")
            .replace("]","")
            .toFloat()
        return priceData
    }

    // Returns Date as X axis in the chart
//    override fun getX(dateIndex: Int): Float {
//        val dateData = chartData[dateIndex].toFloat()
//        println("CHOSEN ONE: " + dateData)
//        return dateData!!
//    }

}