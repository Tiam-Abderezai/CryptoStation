package com.example.cryptostation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cryptostation.adapters.ChartAdapter
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.data.Constants
import com.example.cryptostation.data.API
import com.example.cryptostation.viewmodel.CoinViewModel
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
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var mTV_Symbol: TextView
    private lateinit var mIV_Image: ImageView
    private lateinit var mCoinId: String
    private lateinit var mCoinViewModel: CoinViewModel

    var mCoinResult = java.util.ArrayList<Coin>()
    var mCoinAdapter = CoinAdapter(this, mCoinResult)

    private lateinit var mFavoriteBtn: ImageView
    private var mFavoriteSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Receives the Coin values passed from CoinAdapter
        val intentArray = intent.getSerializableExtra("key_symbol") as ArrayList<String>
        // Initializes the coin id (e.g "bitcoin" or "etherium") used to query coin data
        mCoinId = intentArray?.get(0)
        println("GIMME ID " + mCoinId)
        mCoinViewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        callCoins(mCoinId)



        mTV_Symbol = detail_symbol
        mIV_Image = detail_image
        mFavoriteBtn = detail_star


        mTV_Symbol.setText(intentArray?.get(1))
        Picasso.get().load(intentArray?.get(2)).into(mIV_Image)


        mFavoriteBtn.setOnClickListener {
            clickStar()
        }

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
                    var chartDateAndPrice = mutableMapOf<ArrayList<Long>, ArrayList<Float>>()
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
                                println(
                                    "DISPLAY PRICE " + data.get("prices").asJsonArray.get(i).asJsonArray.get(
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

    private fun clickStar() {
        //https://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22
        val unstarred = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_empty)
        val starred = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_full)

        // Create Star object to save starred coins to SQLite database
        val id = mCoinId
        val symbol = mTV_Symbol.text.toString()

//        val star = Star(0, id, symbol)
        val coin = Coin(0, id, symbol, "", "", 0.0, 0.0, 0.0, 0.0, "")
        if (!mFavoriteSelected) {
            mFavoriteSelected = true
            detail_star.setImageDrawable(starred)
            // Add data to Database
            mCoinViewModel.addCoin(coin)
        } else {
            mFavoriteSelected = false
            detail_star.setImageDrawable(unstarred)
            // Add data to Database
            mCoinViewModel.deleteCoin(coin)
        }
    }

}