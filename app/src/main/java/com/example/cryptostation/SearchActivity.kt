package com.example.cryptostation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.adapters.SearchAdapter
import com.example.cryptostation.models.Coin
import com.example.cryptostation.data.Constants
import com.example.cryptostation.data.API
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    var mCoinResult = java.util.ArrayList<Coin>()
    var mCoinAdapter = SearchAdapter(mCoinResult)

    //creating an arraylist to store users using the data class user
    var mCoins = ArrayList<Coin>()
    //list filtered by the searchbar
    val mFilteredCoins = ArrayList<Coin>()
    //creating our adapter
    val mSearchAdapter = SearchAdapter(mCoins)
    //creating our adapter
    val mFilteredAdapter = SearchAdapter(mFilteredCoins)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
callCoins()

        //adding a layoutmanager
        search_rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //adding some dummy data to the list
//        mCoins.add(Coin("John Oliver", "26","","img",1.0,1.0,1.0,2.0,""))
//        mCoins.add(Coin("ASDSD", "26","","img",1.0,1.0,1.0,2.0,""))
//        mCoins.add(Coin("ZZZZZZZZ", "26","","img",1.0,1.0,1.0,2.0,""))



        //now adding the adapter to recyclerview
        search_rv.adapter = mSearchAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        //getting the search view from the menu
        val searchViewItem = menu.findItem(R.id.menuSearch)

        //getting the search view
        val searchView = searchViewItem.actionView as SearchView

        //making the searchview consume all the toolbar when open
        searchView.maxWidth= Int.MAX_VALUE

        searchView.queryHint = "Search View Hint"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //action while typing

                //hiding the empty textview
                search_tv.visibility= View.GONE

                if (newText.isEmpty()){
                    search_rv.adapter = mSearchAdapter

                }else{
                    mFilteredCoins.clear()
                    for (user in mCoins){
                        if (user.name.toLowerCase().contains(newText.toLowerCase())){
                            mFilteredCoins.add(user)
                        }
                    }
                    if (mFilteredCoins.isEmpty()){
                        //showing the empty textview when the list is empty
                        search_tv.visibility= View.VISIBLE
                    }

                    search_rv.adapter = mFilteredAdapter
                }

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //action when type Enter
                return false
            }

        })

        return true
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
            val response = api.getAllCryptoData("USD", "1h").awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                withContext(Dispatchers.Main) {

                    if (!mCoinResult.isEmpty()) {
                        mCoinResult.clear()
                    }
//                    var coins: Coin
                    // Loop through the retrieved coin list
                    // and store their attributes
                    //        mCoins.add(Coin("John Oliver", "26","","img",1.0,1.0,1.0,2.0,""))

                    for (i: Int in 1..data.size - 1) {
                        mCoins.add(Coin(
                            0,
                            data?.get(i)!!.coinId,
                            data?.get(i)!!.symbol,
                            data?.get(i)!!.name,
                            data?.get(i)!!.imageUrl,
                            data?.get(i)!!.currentPrice,
                            data?.get(i)!!.price_change_24h,
                            data?.get(i)!!.price_change_percentage_1h_in_currency,
                            data?.get(i)!!.price_change_percentage_24h_in_currency,
                            "USD"
                        ))

                        search_rv.adapter = mSearchAdapter

                        // When successful, stop showing refresh
//                        mCoinAdapter.notifyItemRangeChanged(0, mCoinAdapter.itemCount)

//                        frag_market_reflay.isRefreshing = false
                    }
                }
            } else {
                println("FAILED")
            }
        }
    }
}