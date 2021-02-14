package com.example.cryptostation.adapters.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.data.SharedPref
import com.example.cryptostation.models.settings.Language
import kotlinx.android.synthetic.main.list_language.view.*

class LanguageAdapter(private val context: Context?, private var items: MutableList<Language>) :
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    val mainHandler = Handler()


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_language, parent, false)

        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtLanguage: TextView? = null
        var cbxCheckbox: CheckBox? = null

        init {
            this.txtLanguage = view.findViewById(R.id.list_language_language)
            this.cbxCheckbox = view.findViewById(R.id.list_language_checkbox)
        }
    }


    private var languages = mutableListOf<Language>()

    init {
        languages.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language = items[position]
        holder.txtLanguage?.text = language.language
        holder.cbxCheckbox?.isChecked = language.checked

        // SharedPref used to save language selected
        val sharedPref = SharedPref(holder.itemView.context)

        // If the user clicks on the text or anywhere besides
        // the checkbox:
        holder.itemView.setOnClickListener() {
            notifyDataSetChanged()
            when (position) {
                0 ->
                    Handler().postDelayed({
                        holder.cbxCheckbox?.toggle()
                        sharedPref.setLanguage(holder.itemView.context, language.language)
                    }, 300)
                1 ->
                    Handler().postDelayed({
                        holder.cbxCheckbox?.toggle()
                        sharedPref.setLanguage(holder.itemView.context, language.language)
                    }, 300)
                2 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)

                3 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)

                4 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)
            }

            // SharedPreference saves the selected language
            sharedPref.writePrefStatus(true)

        }

        // If the user clicks on the toggle instead of the text or anywhere else
        holder.cbxCheckbox?.setOnClickListener {
            notifyDataSetChanged()
            when (position) {
                0 ->
                    Handler().postDelayed({
                        holder.cbxCheckbox?.toggle()
                        sharedPref.setLanguage(holder.itemView.context, language.language)
                    }, 300)
                1 ->
                    Handler().postDelayed({
                        holder.cbxCheckbox?.toggle()
                        sharedPref.setLanguage(holder.itemView.context, language.language)
                    }, 300)
                2 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)

                3 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)

                4 -> Handler().postDelayed({
                    holder.cbxCheckbox?.toggle()
                    sharedPref.setLanguage(holder.itemView.context, language.language)
                }, 300)
            }

            // SharedPreference saves the selected language
            sharedPref.writePrefStatus(true)
        }

    }

}


