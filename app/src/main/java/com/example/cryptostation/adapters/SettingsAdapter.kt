package com.example.cryptostation.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.DetailActivity
import com.example.cryptostation.R
import com.example.cryptostation.data.SharedPref
import com.example.cryptostation.models.Setting
import com.squareup.picasso.Picasso

class SettingsAdapter(private val context: Context?, private var items: List<Setting>) :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_setting, parent, false)

        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgIcon: ImageView? = null
        var txtName: TextView? = null
        var txtAction: TextView? = null

        init {
            this.imgIcon = view?.findViewById(R.id.list_setting_icon)
            this.txtName = view?.findViewById(R.id.list_setting_name)
            this.txtAction = view?.findViewById(R.id.list_setting_action)
        }
    }


    private var settings = mutableListOf<Setting>()

    init {
        settings.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var setting = items[position]


        // Pass Drawable resources to ImageView:
        // https://stackoverflow.com/questions/63102180/android-change-recyclerview-item-icon-through-adapter
        holder.imgIcon?.setImageResource(setting.icon)
        holder?.txtName?.text = setting.name
        holder?.txtAction?.text = setting.action.toString()

//        val intentArray = ArrayList<String>()
//        intentArray.add(coins.coinId)
//        intentArray.add(coins.symbol.toUpperCase())
//        intentArray.add(coins.imageUrl)
//        val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//        intent.putExtra("key_symbol", intentArray)
        holder.itemView.setOnClickListener {
//            holder.itemView.context.startActivity(intent)
            println("")
        }

    }
}