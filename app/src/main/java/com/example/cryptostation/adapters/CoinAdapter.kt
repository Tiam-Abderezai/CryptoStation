package com.example.cryptostation.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.DetailActivity
//import com.example.cryptostation.DetailActivity
//import com.example.cryptostation.MainActivity
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin
import com.example.cryptostation.data.SharedPref
import com.squareup.picasso.Picasso

class CoinAdapter(private val context: Context?, private var items: List<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    private lateinit var mPref: SharedPref

    //    private lateinit var mContext: Context
    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int) = items[position]


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_coin, parent, false)

        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgImage: ImageView? = null
        var txtSymbol: TextView? = null
        var txtPrice: TextView? = null

        init {
            this.imgImage = view?.findViewById(R.id.list_coin_image)
            this.txtSymbol = view?.findViewById(R.id.list_coin_symbol)
            this.txtPrice = view?.findViewById(R.id.list_coin_price)
        }
    }


    private var coins = mutableListOf<Coin>()

    init {
        coins.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var coins = items[position]

        Picasso.get().load(coins.imageUrl).into(holder?.imgImage)
        holder?.txtSymbol?.text = coins.symbol.toUpperCase()
        holder?.txtPrice?.text = coins.fiat_symbol + " " + coins.currentPrice.toString()
//        println(items.get(position).price_change_percentage_1h_in_currency)
        if (items.get(position).price_change_percentage_1h_in_currency > 0.0 ||
            items.get(position).price_change_percentage_24h_in_currency > 0.0
        ) {
            holder?.txtPrice?.setTextColor(Color.parseColor("#4CAF50"))
        } else {
            holder?.txtPrice?.setTextColor(Color.parseColor("#F30421"))
        }
        // When crypto coin item is clicked, opens Detail page for the coin
        // Sends coin data to it like symbol and image
        val intentArray = ArrayList<String>()
        intentArray.add(coins.coinId)
        intentArray.add(coins.symbol.toUpperCase())
        intentArray.add(coins.imageUrl)
        val intent = Intent(holder.itemView.context, DetailActivity::class.java)
        intent.putExtra("key_symbol", intentArray)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(intent)
        }

    }


}
