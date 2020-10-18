package com.example.cryptostation

//import com.example.cryptostation.adapters.CoinAdapter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.utils.data.Constants
import com.example.cryptostation.utils.network.API
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    val mainHandler = Handler(Looper.getMainLooper())
    var result = ArrayList<Coin>()
    var adapter = CoinAdapter(result)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.main_reclay)
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = adapter
        // Set itemAnimator to null so icon doesn't blink when updated
        mRecyclerView?.itemAnimator = null
        // When swipe down, refresh page (callCoins() again)
        main_reflay.setOnRefreshListener {
            main_reflay.isRefreshing = true
        }
        mainHandler.post(object : Runnable {
            override fun run() {
                callCoins()
                mainHandler.postDelayed(this, 3000)
            }
        })
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
//            .client(okHttpClient)
            .build()
            .create(API::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCryptoData().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {

                    if (!result.isEmpty()) {
                        result.clear()
                    }
                    var coins: Coin
                    // Loop through the retrieved coin list
                    // and store their attributes
                    for (i: Int in 1..data.size - 1) {
                        coins = Coin(
                            data?.get(i)!!.id,
                            data?.get(i)!!.symbol,
                            data?.get(i)!!.name,
                            data?.get(i)!!.imageUrl,
                            data?.get(i)!!.currentPrice
                        )

                        // Add collected coin attributes to result array
                        result.add(coins)

                        if (coins.symbol.equals("bch")) {
                            println(coins.symbol + " price: " + coins.currentPrice)
                        }
                        if (coins.symbol.equals("xmr")) {
                            println(coins.symbol + " price: " + coins.currentPrice)
                        }
                        // When successful, stop showing refresh
                        adapter.notifyItemRangeChanged(0, adapter.itemCount)

                        main_reflay.isRefreshing = false
                    }
                }
            } else {
                println("FAILED")
            }
        }
    }
}


