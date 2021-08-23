//package com.example.cryptostation.adapters
//
//import android.graphics.RectF
//import com.example.cryptostation.models.Coin
//import com.jjoe64.graphview.GraphView
//import com.robinhood.spark.SparkAdapter
//import kotlin.math.nextTowards
//import kotlin.math.withSign
//
//
//// ChartAdapter used to display the Sparkline graph depicting Date (X) price (Y) axises inside DetailActivity
//// It receives this data from DetailActivity inside callCoin() method after receiving coin data using Retrofit2
////                    var chartArray = ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>()
//class ChartAdapter(private val chartArray: ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>) :
//    GraphView() {
////    override fun getCount() = chartArray.size
//////    var chartArray = ArrayList<Array<LongArray>>()
////
////    override fun getItem(index: Int) = chartArray[index]
////
////    // Returns Price as Y axis in the chart
////    override fun getY(index: Int): Float {
////        hasBaseLine()
////        baseLine
////        // Extracts the price from the map store as value
////        // Uses regex to clean "[" and "]" then casts to float
////        // Then returns and displays price on the chart
////        println("Y AXIS: " + chartArray.get(index))
////        val priceData = chartArray.get(index)
////            .values.toString()
////            .replace("[","")
////            .replace("]","")
////            .toFloat()
////        return priceData
////    }
////
////    override fun getX(index: Int): Float {
////        val timeData = chartArray.get(index)
////            .keys.toString()
////            .replace("[","")
////            .replace("]","")
////            .toFloat()
////
//////        println("X AXIS: " + chartArray.get(index)
//////            .keys.toString())
////        return timeData
////    }
////
////    override fun hasBaseLine(): Boolean {
////        return super.hasBaseLine()
////    }
////
////    override fun getBaseLine(): Float {
////        return super.getBaseLine()
////    }
////
////    // Returns Date as X axis in the chart
//////    override fun getX(dateIndex: Int): Float {
//////        val dateData = chartData[dateIndex].toFloat()
//////        println("CHOSEN ONE: " + dateData)
//////        return dateData!!
//////    }
//
//}