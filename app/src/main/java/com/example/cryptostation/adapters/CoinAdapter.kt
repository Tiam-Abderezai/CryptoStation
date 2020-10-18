package com.example.cryptostation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin
import com.squareup.picasso.Picasso

class CoinAdapter(private var items: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int) = items[position]

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.txtPrice?.text = getItem(position).toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_coin, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var imgImage: ImageView? = null
        var txtSymbol: TextView? = null
        var txtPrice: TextView? = null

        init {
            this.imgImage = row?.findViewById(R.id.list_coin_image)
            this.txtSymbol = row?.findViewById(R.id.list_coin_symbol)
            this.txtPrice = row?.findViewById(R.id.list_coin_price)
        }
    }


    private val coins = mutableListOf<Coin>()

    init {
        coins.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var coins = items[position]
        Picasso.get().load(coins.imageUrl).into(holder?.imgImage)
        holder?.txtSymbol?.text = coins.symbol.toUpperCase()
        holder?.txtPrice?.text = "$" + coins.currentPrice.toString()
    }

}