package com.example.cryptostation.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.DetailActivity
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin
import com.squareup.picasso.Picasso

class SearchAdapter (val items: ArrayList<Coin>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_coin, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
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



    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }

    //the class is hodling the list view
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

//        fun bindItems(coin: Coin) {

            var imgImage: ImageView? = null
            var txtSymbol: TextView? = null
            var txtPrice: TextView? = null

            init {
                this.imgImage = view?.findViewById(R.id.list_coin_image)
                this.txtSymbol = view?.findViewById(R.id.list_coin_symbol)
                this.txtPrice = view?.findViewById(R.id.list_coin_price)
            }

    }

    private val coins = mutableListOf<Coin>()

    init {
        coins.addAll(items)
    }


}