package com.example.cryptostation.fragments

import News
import Results
import Source
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.adapters.NewsAdapter
import com.example.cryptostation.models.Coin
//import com.example.cryptostation.models.News
import com.example.cryptostation.utils.data.Constants
import com.example.cryptostation.utils.network.API
import kotlinx.android.synthetic.main.fragment_market.*
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

class NewsFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null
    var mNewsResult = ArrayList<Results>()
    var mNewsSource = ArrayList<Source>()
    var mNewsAdapter = NewsAdapter(mNewsResult)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callNews()
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        // Display list of coins using RecyclerView
        mRecyclerView = view.findViewById(R.id.frag_news_reclay)
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mNewsAdapter
        // Set itemAnimator to null so icon doesn't blink when updated
        mRecyclerView?.itemAnimator = null

        return view
    }

    // Api call to get crypto news data
    private fun callNews() {
        println("CALL NEWS")
        val okHttpClient: OkHttpClient by lazy {
            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
            builder.build()
        }
        val api = Retrofit.Builder()
            .baseUrl("https://cryptopanic.com/api/posts/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(API::class.java)


        val currenciesList: List<String>

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCryptoNews("BTC", "title", "important", true).awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {
                    println("NEWS SIZE " + data.results?.get(0))
                    println("NEWS SIZE " + data.results?.get(1))
//                    println("NEWS IS " + data?.get(1).filter)
//                    println("NEWS IS " + data?.get(2).public)
//                    if (!mCoinResult.isEmpty()) {
//                        mCoinResult.clear()
//                    }


                    var news: Results

                    // Loop through the retrieved coin list
                    // and store their attributes
                    for (i: Int in 1..data.results?.size!! - 1) {
                        news = Results(
                            data.results?.get(i)!!.title,
                            data.results?.get(i)!!.url,
                            data.results?.get(i)!!.source
//                            data?.get(i)!!.public
                        )

                                                println(data.results?.get(i)!!.source)
                        // Add collected news attributes to result array
                        mNewsResult.add(news)

                        mNewsAdapter.notifyItemRangeChanged(0, mNewsAdapter.itemCount)
                    }


//                    println("NEW IS " + news.coinSymbol.toString())


//                        if (coins.symbol.equals("eth")) {
//                            println(coins.symbol + " price: " + coins.currentPrice)
//                        }
//                        if (coins.symbol.equals("xrp")) {
//                            println(coins.symbol + " price: " + coins.currentPrice)
//                        }
//                        println(result)
                    // When successful, stop showing refresh

                }

            } else {
                println("FAILED NEWS")
            }
        }
    }


}