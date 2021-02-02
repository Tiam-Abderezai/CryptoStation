package com.example.cryptostation.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.cryptostation.models.News


class NewsAdapter(private var items: List<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_news, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var imgImage: ImageView? = null
        var txtTitle: TextView? = null
        var txtDomain: TextView? = null

        init {
            this.txtTitle = row.findViewById(R.id.list_news_title)
        }
    }


    private val newsResults = mutableListOf<News>()

    init {
        newsResults.addAll(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = items[position]
        holder.txtTitle?.text = news.title
        val url: String = news.url
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val bundle = Bundle()
        bundle.putBoolean("new_window", true)
        intent.putExtras(bundle)
        holder.itemView.setOnClickListener {
            startActivity(holder.itemView.context, intent, bundle)
        }

    }
}