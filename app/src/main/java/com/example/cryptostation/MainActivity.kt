package com.example.cryptostation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.adapters.CoinAdapter
//import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.utils.network.API
import com.example.cryptostation.utils.network.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_coin.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.main_reclay)

        // When swipe down, refresh page (callCoins() again)
        main_reflay.setOnRefreshListener {
            main_reflay.isRefreshing = true
            callCoins()
        }
    }

    // Api call to get crypto coin data
    private fun callCoins() {
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
            .client(okHttpClient)
            .build()
            .create(API::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCryptoData().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                println("SUCCESS")
                withContext(Dispatchers.Main) {
                    println("Coin Symbol: " + response.body())
                    var result = ArrayList<Coin>()
                    var coins: Coin
//                    var i:Int = data.size
                    for (i: Int in 1..data.size - 1) {
                        coins = Coin(
                            data?.get(i)!!.id,
                            data?.get(i)!!.symbol,
                            data?.get(i)!!.name,
                            data?.get(i)!!.image,
                            data?.get(i)!!.currentPrice
                        )
//                        println(coins.currentPrice)
                        result.add(coins)

                        // When successful, stop showing refresh
                        main_reflay.isRefreshing = false

                    }
                    var adapter = CoinAdapter(result)
                    val layoutManager = LinearLayoutManager(applicationContext)
                    mRecyclerView?.layoutManager = layoutManager
                    mRecyclerView?.itemAnimator = DefaultItemAnimator()
                    mRecyclerView?.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            } else {
                println("FAILED")
            }


        }
    }
}