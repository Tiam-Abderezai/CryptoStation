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
import kotlinx.android.synthetic.main.list_coin.view.*

class StarredCoinAdapter(private val context: Context?, private var coinList: List<Coin>) :
    RecyclerView.Adapter<StarredCoinAdapter.MyViewHolder>() {

//    private var coinList = emptyList<Coin>()

//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgImage: ImageView? = null
        var txtSymbol: TextView? = null
        var txtPrice: TextView? = null

        init {
            this.imgImage = view?.findViewById(R.id.list_coin_image)
            this.txtSymbol = view?.findViewById(R.id.list_coin_symbol)
            this.txtPrice = view?.findViewById(R.id.list_coin_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_coin, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return coinList.size
    }


    private var coins = mutableListOf<Coin>()

    init {
        coins.addAll(coinList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var coins = coinList[position]

        println("PIRATE " + coins)
//        Picasso.get().load(coins.imageUrl).into(holder?.imgImage)
        holder?.txtSymbol?.text = coins.symbol.toUpperCase()
        holder?.txtPrice?.text = coins.fiat_symbol + " " + coins.currentPrice.toString()
//        println(items.get(position).price_change_percentage_1h_in_currency)
        if (coinList.get(position).price_change_percentage_1h_in_currency > 0.0 ||
            coinList.get(position).price_change_percentage_24h_in_currency > 0.0
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
        //        val currentItem = coinList[position]
//        holder.itemView.list_coin_symbol.text = currentItem.symbol
//        holder.itemView.list_coin_price.text = currentItem.currentPrice.toString()
//        println("CURRENT ITEM: ${currentItem.currentPrice}")
////        holder.itemView.list_coin.setOnClickListener {
////            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
////            holder.itemView.findNavController().navigate(action)
////        }
    }

    fun setData(coin: List<Coin>) {
        this.coinList = coin
        notifyDataSetChanged()
    }
}

