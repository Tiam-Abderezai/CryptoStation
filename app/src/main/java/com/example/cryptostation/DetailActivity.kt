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
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var mTV_Symbol: TextView
    private lateinit var mIV_Image: ImageView
    private lateinit var coinData: List<Coin>

    var mCoinResult = java.util.ArrayList<Coin>()
    var mCoinAdapter = CoinAdapter(this, mCoinResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        callCoins()

        val intentArray = intent.getSerializableExtra("key_symbol") as ArrayList<String>

        mTV_Symbol = findViewById(R.id.detail_symbol)
        mIV_Image = findViewById(R.id.detail_image)

        mTV_Symbol.setText(intentArray?.get(0))
        Picasso.get().load(intentArray?.get(1)).into(mIV_Image)

    }


    // Api call to get crypto coin data
    private fun callCoins() {
        val api = Retrofit.Builder()
            .baseUrl(Constants.URL_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()
            .create(API::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCryptoData("USD", "").awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {


                    var coins: Coin
                    // Loop through the retrieved coin list
                    // and store their attributes
                    for (i: Int in 1..data.size - 1) {
                        coins = Coin(
                            data?.get(i)!!.id,
                            data?.get(i)!!.symbol,
                            data?.get(i)!!.name,
                            data?.get(i)!!.imageUrl,
                            data?.get(i)!!.currentPrice,
                            data?.get(i)!!.price_change_24h,
                            data?.get(i)!!.price_change_percentage_1h_in_currency,
                            data?.get(i)!!.price_change_percentage_24h_in_currency,
                            "USD"
                        )

                        // Add collected coin attributes to result array
                        mCoinResult.add(coins)
                        coinData = mCoinResult
                        val adapter = ChartAdapter(coinData)
                        detail_chart.adapter = adapter
                    }
                    // When successful, stop showing refresh
                    mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)
                }
            } else {
                println("FAILED")
            }
        }
    }


}