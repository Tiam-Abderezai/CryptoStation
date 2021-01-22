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
import com.example.cryptostation.adapters.CoinAdapter
import com.example.cryptostation.databinding.FragmentCoinBinding
import com.example.cryptostation.models.Coin
import com.example.cryptostation.utils.data.Constants
import com.example.cryptostation.utils.data.SharedPref
import com.example.cryptostation.utils.network.API
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable
import java.util.ArrayList

class CoinFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null
    private var mCurrencySpinner: Spinner? = null
    private var mDurationSpinner: Spinner? = null

    val mainHandler = Handler(Looper.getMainLooper())
    var mCoinResult = ArrayList<Coin>()
    var mCoinAdapter = CoinAdapter(context, mCoinResult)
    var mDuration: String = ""
    var mFiatName: String = ""
    var mFiatSymbol: String = ""
    private lateinit var mPref: SharedPref
    private lateinit var coins: Coin
    private lateinit var binding: FragmentCoinBinding

//    val mViewModel = ViewModelProviders.of(this)[CoinViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callCoins()


        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        // Display list of coins using RecyclerView
        mRecyclerView = view.findViewById(R.id.frag_coin_reclay)
        mCurrencySpinner = view.findViewById(R.id.frag_coin_spin_currency)
        mDurationSpinner = view.findViewById(R.id.frag_coin_spin_duration)
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mCoinAdapter
        // Saves scroll position upon orientation screen changed
        // https://stackoverflow.com/questions/52587745/how-to-save-and-restore-scrolling-position-of-the-recyclerview-in-a-fragment-whe
        mCoinAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        // Set itemAnimator to null so icon doesn't blink when updated
        mRecyclerView?.itemAnimator = null
        // When swipe down, refresh page (callCoins() again)
//        frag_market_reflay.setOnRefreshListener {
//            frag_market_reflay.isRefreshing = true
//        }
        // Run the api call to get and display coin data every second
        mainHandler.post(object : Runnable {
            override fun run() {
                callCoins()
                mainHandler.postDelayed(this, 1000)
            }
        })


        var currencyAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            Constants.fiatArrayName
        )


        var durationAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            Constants.priceArrayDuration
        )

        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mDurationSpinner!!.adapter = durationAdapter
        mCurrencySpinner!!.adapter = currencyAdapter

        // mPref is used to save the state of the spinner positions
        mPref = SharedPref(activity!!.applicationContext)
        val savedSelectedDuration: Int = mPref.getDuration(activity!!.applicationContext)
        val savedSelectedCurrency: Int = mPref.getFiat(activity!!.applicationContext)
        mCurrencySpinner?.setSelection(savedSelectedCurrency, true)
        mDurationSpinner?.setSelection(savedSelectedDuration, true)
        mDuration = Constants.priceArrayDuration.get(savedSelectedDuration)
        mFiatName = Constants.fiatArrayName.get(savedSelectedCurrency)
        mFiatSymbol = Constants.fiatArraySymbol.get(savedSelectedCurrency)
        mPref.setDuration(activity!!.applicationContext, savedSelectedDuration)
        mPref.setFiat(activity!!.applicationContext, savedSelectedCurrency)
        mPref.writePrefStatus(true)
//        callCoins()

        // When successful, stop showing refresh
        mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)


        // Select currency spinner
        mCurrencySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mFiatName = Constants.fiatArrayName.get(position)
                mFiatSymbol = Constants.fiatArraySymbol.get(position)
                mPref.setFiat(activity!!.applicationContext, position)
                mPref.writePrefStatus(true)
//                callCoins()
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


        // Select duration spinner
        mDurationSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mDuration = Constants.priceArrayDuration.get(position)
                mPref.setDuration(activity!!.applicationContext, position)
                mPref.writePrefStatus(true)
                callCoins()
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


    // Api call to get crypto coin data
    private fun callCoins() {
        //Code above causes the below error and below that is the stackoverflow info on it:
        //java.lang.IllegalStateException: Can't access ViewModels from detached fragment
        //https://stackoverflow.com/questions/50246796/caused-by-java-lang-illegalstateexception-cant-create-viewmodelprovider-for-de



//        val coinMutableList = mutableListOf<Coin>()
//        var coinAdapter = CoinAdapter(context, coinMutableList)
//
//        mViewModel.getCoins().observe(viewLifecycleOwner, Observer { coinsSnapshot ->
//            // Update UI upon Lifecycle
//            println("SNAPSHOT: "+coinsSnapshot[0])
//            coinMutableList.clear()
//            coinMutableList.addAll(coinsSnapshot)
//            coinAdapter.notifyDataSetChanged()
//        })


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
            val response = api.getCryptoData(mFiatName, mDuration).awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {

                    if (!mCoinResult.isEmpty()) {
                        mCoinResult.clear()
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
                            data?.get(i)!!.currentPrice,
                            data?.get(i)!!.price_change_24h,
                            data?.get(i)!!.price_change_percentage_1h_in_currency,
                            data?.get(i)!!.price_change_percentage_24h_in_currency,
                            mFiatSymbol
                        )

                        // Add collected coin attributes to result array
                        mCoinResult.add(coins)

                        if (coins.symbol.equals("eth")) {
                            println(coins.symbol + " price: " + coins.currentPrice)
                        }
//                        if (coins.symbol.equals("xrp")) {
//                            println(coins.symbol + " price: " + coins.currentPrice)
//                        }
//                        println(result)
                        // When successful, stop showing refresh
                        mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)

//                        frag_market_reflay.isRefreshing = false
                    }
                }
            } else {
                println("FAILED")
            }
        }
    }


}
