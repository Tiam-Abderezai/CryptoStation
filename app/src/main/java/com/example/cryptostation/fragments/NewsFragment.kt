package com.example.cryptostation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.adapters.NewsAdapter

import com.example.cryptostation.models.News
import com.example.cryptostation.data.API
import com.example.cryptostation.data.Constants
import com.example.cryptostation.data.SharedPref
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
    private var mNewsResult = ArrayList<News>()
    private var mNewsAdapter = NewsAdapter(mNewsResult)

    //    var mNewsSource = ArrayList<Source>()
    private var mCoinSpinner: Spinner? = null
    private lateinit var mPref: SharedPref
    var mCoin: String = ""
    val mainHandler = Handler(Looper.getMainLooper())



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        // Display list of coins using RecyclerView


        mRecyclerView = view.findViewById(R.id.frag_news_reclay)
        mCoinSpinner = view.findViewById(R.id.frag_news_spin_coin)
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mNewsAdapter
        // Saves scroll position upon orientation screen changed
        // https://stackoverflow.com/questions/52587745/how-to-save-and-restore-scrolling-position-of-the-recyclerview-in-a-fragment-whe
        mNewsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        // Set itemAnimator to null so icon doesn't blink when updated
        mRecyclerView?.itemAnimator = null

        callNews()

        mainHandler.post(object : Runnable {
            override fun run() {
                callNews()
                mainHandler.postDelayed(this, 1000)
            }
        })

        var coinAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            Constants.coinArray
        )
        coinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mCoinSpinner!!.adapter = coinAdapter

        // mPref is used to save the state of the spinner positions
        mPref = SharedPref(activity!!.applicationContext)
        val savedSelectedCoin: Int = mPref.getCoin(activity!!.applicationContext)
        mCoinSpinner?.setSelection(savedSelectedCoin, true)
        mCoin = Constants.coinArray.get(savedSelectedCoin)
        mPref.setCoin(activity!!.applicationContext, savedSelectedCoin)
        mPref.writePrefStatus(true)

        mNewsAdapter.notifyItemRangeChanged(0, mNewsAdapter.itemCount)

        // Select coin spinner
        mCoinSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mCoin = Constants.coinArray.get(position)
                mPref.setCoin(activity!!.applicationContext, position)
                mPref.writePrefStatus(true)
                callNews()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

        }

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

        GlobalScope.launch(Dispatchers.IO) {
            println("HAHA " + mCoin)
            val response = api.getCryptoNews(mCoin, "title", true, "en").awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {
                    println("NEWS RESULTS ARE: " + data.get("results").asJsonArray.size())

                    if (!mNewsResult.isEmpty()) {
                        mNewsResult.clear()
                    }
                    var news: News
                    var newsMutableMap = mutableMapOf<String, String>()
                    // Loop through the retrieved coin list
                    // and store their attributes
                    for (i: Int in 1..data.get("results").asJsonArray.size() - 1) {

                        news = News(
                            data?.get("results").asJsonArray.get(i).asJsonObject.get("title")
                                .toString(),
                            data?.get("results").asJsonArray.get(i).asJsonObject.get("url")
                                .toString().replace("\"", "")
                        )
                        // Add collected news attributes to result array
                        mNewsResult.add(news)
                        mNewsAdapter.notifyItemRangeChanged(0, mNewsAdapter.itemCount)
                    }

                }

            } else {
                println("FAILED NEWS")
            }
        }
    }


}