package com.example.olympics.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.olympics.R

class NewsAdapter(private val context: Context, private val listener: NewsClicked): RecyclerView.Adapter<NewsViewHolder>() {

    var newsList: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        val viewHolder =  NewsViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(newsList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentNewsItem = newsList[position]
        holder.articleTitle.text = currentNewsItem.getTitle()
        holder.articleAuthor.text = currentNewsItem.getAuthor()
        holder.articleDate.text = getDate(currentNewsItem.getPublishedAt())
        holder.articleDescription.text = currentNewsItem.getDescription()

        Glide.with(context).load(currentNewsItem.getUrlImage()).placeholder(R.drawable.no_image).into(holder.articleImage)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateListItems(list: ArrayList<News>){
        newsList.clear()
        newsList.addAll(list)

        notifyDataSetChanged()
    }

    private fun getDate(date: String): String{
        val year = date.substring(0, 4)
        val currentDate = date.substring(8, 10)
        val month = when(date[6] - '0'){
            1-> "Jan"
            2-> "Feb"
            3-> "Mar"
            4-> "Apr"
            5-> "May"
            6-> "Jun"
            7-> "Jul"
            8-> "Aug"
            9-> "Sep"
            10-> "Oct"
            11-> "Nov"
            else-> "Dec"
        }

        return "$currentDate $month $year"
    }

}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val articleImage: ImageView = itemView.findViewById(R.id.articleImage)
    val articleTitle: TextView = itemView.findViewById(R.id.articleTitle)
    val articleDescription: TextView = itemView.findViewById(R.id.articleDescription)
    val articleDate: TextView = itemView.findViewById(R.id.articleDate)
    val articleAuthor: TextView = itemView.findViewById(R.id.articleAuthor)

}

interface NewsClicked{
    fun onItemClicked(news: News)
}