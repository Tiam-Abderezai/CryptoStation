package com.example.cryptostation.fragments


//import com.example.cryptostation.models.News
//import Results
//import Source
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.adapters.NewsAdapter

import com.example.cryptostation.models.News
import com.example.cryptostation.data.API
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

    private  var mRecyclerView: RecyclerView? = null
    private var mNewsResult = ArrayList<News>()
    private var mNewsAdapter = NewsAdapter(mNewsResult)
//    var mNewsSource = ArrayList<Source>()


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
        // Saves scroll position upon orientation screen changed
        // https://stackoverflow.com/questions/52587745/how-to-save-and-restore-scrolling-position-of-the-recyclerview-in-a-fragment-whe
        mNewsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
            val response = api.getCryptoNews("BTC", "title",  true,"en").awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {
                    println("NEWS RESULTS ARE: " + data.get("results").asJsonArray.size())
//                    println("NEWS SIZE " + data.results?.get(1))
//                    println("NEWS IS " + data?.get(1).filter)
//                    println("NEWS IS " + data?.get(2).public)
//                    if (!mCoinResult.isEmpty()) {
//                        mCoinResult.clear()
//                    }

                    var news: News
                    var newsMutableMap = mutableMapOf<String, String>()
                    // Loop through the retrieved coin list
                    // and store their attributes
                    for (i: Int in 1..data.get("results").asJsonArray.size() - 1) {

                        news = News(
                            data?.get("results").asJsonArray.get(i).asJsonObject.get("title").toString(),
                            data?.get("results").asJsonArray.get(i).asJsonObject.get("url").toString().replace("\"","")
//                            data?.get(i)!!.url,
//                            data.?.get(i)!!.source
//                            data?.get(i)!!.public
                        )
                        println(
                            "SOURCES ARE: " + data?.get("results").asJsonArray.get(i).asJsonObject.get(
                                "url"
                            )
                        )
//                                                println(data.results?.get(i)!!.source)
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