package com.example.cryptostation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import com.example.cryptostation.adapters.ChartAdapter
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.adapters.StarredCoinAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.data.Constants
import com.example.cryptostation.data.API
import com.example.cryptostation.viewmodel.CoinViewModel
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*


class DetailActivity : AppCompatActivity() {

    private lateinit var mTV_Symbol: TextView
    private lateinit var mTV_Name: TextView
    private lateinit var mTV_Price: TextView
    private lateinit var mTV_NewsTitle: TextView

    private lateinit var mIV_Image: ImageView
    private lateinit var mCoinId: String
    private lateinit var mCoinViewModel: CoinViewModel
    private lateinit var mSeries: LineGraphSeries<DataPoint>
    private lateinit var viewport: Viewport
    val x = mutableListOf(0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0)
    val y = mutableListOf(0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0)

    var mCoinResult = java.util.ArrayList<Coin>()
    var mCoinAdapter = CoinAdapter(this, mCoinResult)
    var mStarredCoinAdapter = StarredCoinAdapter(this, mCoinResult)
    private lateinit var mFavoriteBtn: ImageView
    private var mFavoriteSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // Receives the Coin values passed from CoinAdapter
        val intentArray = intent.getSerializableExtra("key_symbol") as ArrayList<String>
        // Initializes the coin id (e.g "bitcoin" or "etherium") used to query coin data
        mCoinId = intentArray?.get(0)
        callCoins(mCoinId)

        setupEventListeners()
        detail_history_radio_group.detail_history_1hr.isChecked = true
    }


    // Api call to get crypto coin data
    private fun callCoins(coinId: String) {

        val okHttpClient: OkHttpClient by lazy {
            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
            builder.build()
        }
        this.let {
            mCoinViewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
            mCoinViewModel.readAllData.observe(
                this,
                Observer { starredCoins ->
                    mStarredCoinAdapter.setData(starredCoins)
                })
        }

        val api = Retrofit.Builder()
            .baseUrl(Constants.URL_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(API::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getACryptoData(coinId).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    withContext(Dispatchers.Main) {
                        // Subtracting System.currentTimeMillis() by 7 days:
                        // https://stackoverflow.com/questions/15607500/subtracting-two-days-from-current-date-in-epoch-milliseconds-java
                        val currentTime: Long = System.currentTimeMillis() / 1000L
                        val lastWeek = currentTime - 7 * 24 * 60 * 60
                        println("LAST WEEK: " + lastWeek)
                        println("TIMESTAMP: " + currentTime)
                        println(
                            "SHOW ME " + data.get("prices").asJsonArray.size()
                        )
//                    var hoursInWeek: Int = data.get("prices").asJsonArray

                        var chartDate = ArrayList<Long>()
                        var chartPrice = ArrayList<Float>()
                        var chartDateAndPrice = mutableMapOf<ArrayList<Long>, ArrayList<Float>>()

                        val chartArray = ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>()
                        // ArrayList of history for X axis
                        var xHistoryFloats: ArrayList<Long> = arrayListOf(0)
                        // ArrayList of prices for Y axis
                        val yPricesDoubles: ArrayList<Double> = arrayListOf(0.0)


                        for (i: Int in 1..data.get("prices").asJsonArray.size() - 1) {
                            println("chart data: " + chartDate)
//                            for (j: Int in 1..)
                            when (i) {
                                1 -> {
                                    chartPrice = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat
                                    )
                                    chartDate = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            0
                                        ).asLong / 1000L
                                    )
                                    println(
                                        "DISPLAY PRICE1 " + data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat / 100
                                    )
                                    chartDateAndPrice = mutableMapOf(chartDate to chartPrice)
                                    chartArray.add(0, chartDateAndPrice)
                                }
                                49 -> {
                                    chartPrice = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat
                                    )
                                    chartDate = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            0
                                        ).asLong / 1000L
                                    )
                                    println(
                                        "DISPLAY PRICE2 " + data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat / 100
                                    )
                                    chartDateAndPrice = mutableMapOf(chartDate to chartPrice)
                                    chartArray.add(0, chartDateAndPrice)
                                }
                                97 -> {
                                    chartPrice = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat
                                    )
                                    chartDate = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            0
                                        ).asLong / 1000L
                                    )
                                    chartDateAndPrice = mutableMapOf(chartDate to chartPrice)
                                    chartArray.add(0, chartDateAndPrice)
                                }
                                145 -> {
                                    chartPrice = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            1
                                        ).asFloat
                                    )
                                    chartDate = arrayListOf(
                                        data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                            0
                                        ).asLong / 1000L
                                    )
                                    chartDateAndPrice = mutableMapOf(chartDate to chartPrice)
                                    chartArray.add(0, chartDateAndPrice)
                                }

                            }


                        }

                        // Receives the Coin values passed from CoinAdapter
                        val intentArray =
                            intent.getSerializableExtra("key_symbol") as ArrayList<String>
                        // Initializes the coin id (e.g "bitcoin" or "etherium") used to query coin data
                        mCoinId = intentArray?.get(0)
                        mTV_Symbol = detail_symbol
                        mIV_Image = detail_image
                        mFavoriteBtn = detail_star
                        mTV_Name = detail_name
                        mTV_Price = detail_price
                        mTV_Symbol.setText(intentArray?.get(1))
                        Picasso.get().load(intentArray?.get(2)).into(mIV_Image)
                        mTV_Name.text = intentArray?.get(0)


                        mFavoriteBtn.setOnClickListener {
                            clickStar()
                        }

                        val coinSize = mCoinViewModel.readAllData.value?.size
                        // Add prices and history to their corresponding arrays
                        for (i in 0..chartArray.size - 1) {
                            xHistoryFloats.add(
                                chartArray.get(i)
                                    .keys.toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .toLong()
                            )
                            yPricesDoubles.add(
                                chartArray.get(i)
                                    .values.toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .toDouble()
                            )

                        }
                        coinSize?.let {
                            var maxY = yPricesDoubles.get(0)
                            var minY = yPricesDoubles.get(0)

                            for (y in 0..chartArray.size - 1) {
                                maxY = Math.max(maxY, yPricesDoubles.get(y))
                                minY = Math.min(minY, yPricesDoubles.get(y))
                            }

                            println("MAX Y IS:" + maxY)
                            println("MIN Y IS:" + minY)

                            mSeries = LineGraphSeries()
                            detail_chart_graph.addSeries(mSeries)
                            viewport = detail_chart_graph.viewport
                            viewport.isYAxisBoundsManual = true
                            viewport.setMinX(0.0)
                            viewport.setMaxX(40.0)

                            viewport.setMinY(minY)
                            viewport.setMaxY(maxY)
                            viewport.isScrollable = true
                            for (i: Int in 0..coinSize!! - 1) {
                                // if coin is starred, it stars the coin
                                if (mCoinId.equals(mCoinViewModel.readAllData.value!!.get(i).coinId.toLowerCase())
                                ) {
                                    val starred = ContextCompat.getDrawable(
                                        applicationContext,
                                        R.drawable.ic_star_full
                                    )
                                    detail_star.setImageDrawable(starred)
                                    println("GIMME ID " + mCoinId)
                                }
                            }
                        }

                        for (p in 0..chartArray.size - 1) {
                            println("X Axxis " + xHistoryFloats.get(p))
                            println("Y Axxis " + yPricesDoubles.get(p))
                            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                            val date = Date(xHistoryFloats.get(p + 1) * 1000)
                            println("HISTORICAL: " + sdf.format(date))
                            mSeries.appendData(
                                DataPoint(
//                                    chartArray.get(p)
////                                    xHistoryDoubles.get(p).toDouble()
                                    x[p]
//                                        .keys.toString()
//                                        .replace("[", "")
//                                        .replace("]", "")
//                                        .toDouble() * 0.0001
                                    ,
                                    yPricesDoubles.get(p)
                                ),
                                true,
                                100
                            )
                        }
                        // When successful, stop showing refresh
                        mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)

                    }
                }
                response.raw().close()
            } catch (ex: Exception) {
                println("FAILED: DetailActivity")
            }

        }
    }

    private fun clickStar() {
        //https://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
        val unstarred = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_empty)
        val starred = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_full)

        // Create Star object to save starred coins to SQLite database
        val id = mCoinId
        val symbol = mTV_Symbol.text.toString()
        val coin = Coin(0, id, symbol, "", "", 0.0, 0.0, 0.0, 0.0, "")
        if (!mFavoriteSelected) {
            mFavoriteSelected = true
            detail_star.setImageDrawable(starred)
            // Add data to Database
            mCoinViewModel.addCoin(coin)
        } else {
            mFavoriteSelected = false
            detail_star.setImageDrawable(unstarred)
            // Remove data to Database
            // todo implement DELETE. It's NOT working.
            mCoinViewModel.deleteCoin(coin)
        }
    }

    private fun setupEventListeners() {
        // Respond to radio button selected events
        // See https://github.com/rpandey1234/Covid19Tracker/blob/master/app/src/main/java/edu/stanford/rkpandey/covid19tracker/MainActivity.kt
        detail_history_radio_group.setOnCheckedChangeListener { group, checkedId ->
            val timeScale = when (checkedId) {
                R.id.detail_history_1hr -> Constants.TimeScale.HOUR_1
                R.id.detail_history_24hr -> Constants.TimeScale.HOUR_24
                R.id.detail_history_7d -> Constants.TimeScale.DAYS_7
                R.id.detail_history_14d -> Constants.TimeScale.DAYS_14
                R.id.detail_history_30d -> Constants.TimeScale.DAYS_30
                R.id.detail_history_200d -> Constants.TimeScale.DAYS_200
                else -> Constants.TimeScale.HOUR_1
            }
            println("SELECTED TIMESCALE" + timeScale.history)
//            callCoins(mCoinId, timeScale)
        }
    }
}