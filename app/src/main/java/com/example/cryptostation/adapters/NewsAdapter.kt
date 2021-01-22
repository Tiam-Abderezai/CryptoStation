package com.example.cryptostation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import News
import Results
import Source
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.cryptostation.utils.data.SharedPref
import com.squareup.picasso.Picasso


class NewsAdapter(private var items: ArrayList<Results>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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

//    override fun getItemViewType(position: Int): Int {
//        val element = items[position]
//        return when (element) {
//            is Results -> 0
//            is Source -
//            else -> throw IllegalArgumentException("Invalid type of data {$position}")
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_news, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var imgImage: ImageView? = null
        var txtTitle: TextView? = null
        var txtDomain: TextView? = null

        init {
//            this.imgImage = row?.findViewById(R.id.list_coin_image)
            this.txtTitle = row.findViewById(R.id.list_news_title)
//            this.txtUrl = row?.findViewById(R.id.list_news_url)
        }
    }


    private val newsResults = mutableListOf<Results>()

    init {
        newsResults.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var news = items[position]
//        Picasso.get().load(news.imageUrl).into(holder?.imgImage)
        holder?.txtTitle?.text = news.title
        val url: String = news.url
        val domain: String = news.source.domain
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val bundle = Bundle()
        bundle.putBoolean("new_window", true)
        intent.putExtras(bundle)
        holder.itemView.setOnClickListener {
            startActivity(holder.itemView.context, intent, bundle)
        }
//        println("URL "+holder?.txtUrl?.text)

//        println(items.get(position).price_change_percentage_1h_in_currency)
    }
}