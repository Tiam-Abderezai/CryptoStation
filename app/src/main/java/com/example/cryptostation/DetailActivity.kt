package com.example.cryptostation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptostation.adapters.ChartAdapter
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.utils.data.Constants
import com.example.cryptostation.utils.network.API
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var mTV_Symbol: TextView
    private lateinit var mIV_Image: ImageView

    //    private lateinit var coinData: List<Coin>
    private lateinit var mCoinData: Coin
    private lateinit var mCoinId: String

    var mCoinResult = java.util.ArrayList<Coin>()
    var mCoinAdapter = CoinAdapter(this, mCoinResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Receives the Coin values passed from CoinAdapter
        val intentArray = intent.getSerializableExtra("key_symbol") as ArrayList<String>
        // Initializes the coin id (e.g "bitcoin" or "etherium") used to query coin data
        mCoinId = intentArray?.get(0)
        println("GIMME ID " + mCoinId)

        callCoins(mCoinId)



        mTV_Symbol = findViewById(R.id.detail_symbol)
        mIV_Image = findViewById(R.id.detail_image)


        mTV_Symbol.setText(intentArray?.get(1))
        Picasso.get().load(intentArray?.get(2)).into(mIV_Image)


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


        val api = Retrofit.Builder()
            .baseUrl(Constants.URL_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()
            .create(API::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getACryptoData(coinId).awaitResponse()

            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {
                    var coins: Coin
                    // Subtracting System.currentTimeMillis() by 7 days:
                    // https://stackoverflow.com/questions/15607500/subtracting-two-days-from-current-date-in-epoch-milliseconds-java
                    val currentTime: Long = System.currentTimeMillis() / 1000L
                    val lastWeek = currentTime - 7 * 24 * 60 * 60
                    println("LAST WEEK: " + lastWeek)
//                    println("Time2: " +currentTime2)
                    println("TIMESTAMP: " + currentTime)
//                    println(
//                        "SHOW ME " + data.get("market_data")
//                            .asJsonObject.get("current_price")
//                            .asJsonObject.get("usd")
//                    )
                    println(
                        "SHOW ME " + data.get("prices").asJsonArray.size()
                    )
//                    var hoursInWeek: Int = data.get("prices").asJsonArray

                    var chartDate = ArrayList<Long>()
                    var chartPrice = ArrayList<Float>()
                    var chartDateAndPrice = mutableMapOf<ArrayList<Long>,ArrayList<Float>>()
                    var chartArray = ArrayList<MutableMap<ArrayList<Long>, ArrayList<Float>>>()

                    for (i: Int in 1..data.get("prices").asJsonArray.size() - 1) {
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
                                println("DISPLAY PRICE " + data.get("prices").asJsonArray.get(i).asJsonArray.get(
                                    1
                                ).asFloat / 100)
                                chartDateAndPrice = mutableMapOf( chartDate to chartPrice)
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
                                chartDateAndPrice = mutableMapOf( chartDate to chartPrice)
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
                                chartDateAndPrice = mutableMapOf( chartDate to chartPrice)
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
                                chartDateAndPrice = mutableMapOf( chartDate to chartPrice)
                                chartArray.add(0, chartDateAndPrice)
                            }

                        }


                    }
//
//                    for (i in 0..chartArray.get(1).size - 1) {
//                        println("PPPRICE" + chartArray.get(1).get(i))
//                    }
//
//                    for (i in 0..chartArray.get(1).size - 1) {
//                        println("DDDate" + chartArray.get(0).get(i))
//                    }
                    val adapter = ChartAdapter(chartArray)
                    detail_chart.adapter = adapter
                    adapter.notifyDataSetChanged()

//                    // When successful, stop showing refresh
                    mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)

                }
            } else {
                println("FAILED")
            }
        }
    }


}