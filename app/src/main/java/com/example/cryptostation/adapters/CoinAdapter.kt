package com.example.cryptostation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin

class CoinAdapter(private var items: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var coins = items[position]
        holder?.txtName?.text = coins.name
        holder?.txtPrice?.text = coins.currentPrice.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_coin, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var txtName: TextView? = null
        var txtPrice: TextView? = null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.list_coin_name)
            this.txtPrice = row?.findViewById<TextView>(R.id.list_coin_price)
        }
    }


}