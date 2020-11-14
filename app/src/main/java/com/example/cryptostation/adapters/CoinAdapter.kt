package com.example.cryptostation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
//import com.example.cryptostation.MainActivity
import com.example.cryptostation.R
import com.example.cryptostation.models.Coin
import com.example.cryptostation.utils.data.SharedPref
import com.squareup.picasso.Picasso
import kotlinx.coroutines.currentCoroutineContext

class CoinAdapter(private var items: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private lateinit var mPref: SharedPref
//    private lateinit var mContext: Context
    override fun getItemCount(): Int {
        return items.size
    }

//    constructor(context: Context) {
//        this.mContext = context
////        mPref = context.getSharedPreferences(
////            context.getResources().getString(R.string.preference_value),
////            Context.MODE_PRIVATE
////        )
//    }


    fun getItem(position: Int) = items[position]

//    @Suppress("UNCHECKED_CAST")
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
//            println("yes")
//        } else {
////            holder.itemView?.setBackgroundColor(Color.GREEN)
////            holder.txtPrice?.text = getItem(position).toString()
//            println("no")
////            println(items.get(position).price_change_percentage_1h_in_currency)
//            if(items.get(position).price_change_percentage_1h_in_currency < 0.0)
//            {
//                }
//            else
//            {        holder?.txtPrice?.setTextColor(Color.parseColor("#F30421"))}
////            holder.txtPrice?.setTextColor(Color.WHITE).toString()
////            holder.txtPrice?.setBackgroundColor(Color.GREEN)
//        }
//
//    }



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
        holder?.txtPrice?.text = coins.fiat_symbol + " " + coins.currentPrice.toString()
//        println(items.get(position).price_change_percentage_1h_in_currency)
        if(items.get(position).price_change_percentage_1h_in_currency > 0.0 ||
            items.get(position).price_change_percentage_24h_in_currency > 0.0 )
        {        holder?.txtPrice?.setTextColor(Color.parseColor("#4CAF50"))}
        else
        {        holder?.txtPrice?.setTextColor(Color.parseColor("#F30421"))}
    }

}