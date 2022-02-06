package com.example.news_g

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener : NewsOnClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private var list: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemnews, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onClick(list[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = list[position]
        holder.titleView.text = currentItem.title
        holder.authorView.text = currentItem.author

        Glide.with(holder.itemView.context).load(currentItem.urlImage).into(holder.urlToImageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        list.clear()
        list.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title_view)
    val authorView: TextView = itemView.findViewById(R.id.author_view)
    val urlToImageView: ImageView = itemView.findViewById(R.id.urlimage)
}

interface NewsOnClicked{
    fun onClick(item : News)
}