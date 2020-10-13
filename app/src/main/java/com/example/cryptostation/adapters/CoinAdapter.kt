package com.example.cryptostation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin
import com.squareup.picasso.Picasso

class CoinAdapter(private var items: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var coins = items[position]
//        holder?.imgImage?.text = coins.image
        holder?.txtSymbol?.text = coins.symbol.toUpperCase()
        holder?.txtPrice?.text = coins.currentPrice.toString()
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
//            Picasso.get(imgImage.context).load(version.imageUrl)
//                .placeholder(R.mipmap.ic_launcher_round)// optional
//                .error(R.drawable.sync)// optional
//                .into(imageView);
//            loadImageUrl("http://i.imgur.com/DvpvklR.pjdfgdfkhng")
            this.txtSymbol = row?.findViewById(R.id.list_coin_symbol)
            this.txtPrice = row?.findViewById(R.id.list_coin_price)
        }
    }


}