package com.example.mydiary

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRVAdapter(private val listener: ClickListener) : RecyclerView.Adapter<DiaryViewHolder>() {
    private val allDiaryNotes = ArrayList<DiaryContent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        val viewHolder = DiaryViewHolder(view)
        viewHolder.deleteButton.setOnClickListener {
            listener.onItemClicked(allDiaryNotes[viewHolder.adapterPosition])
        }

        viewHolder.headingTextView.setOnClickListener {
            Log.e("Heloooooooooooooooooo", allDiaryNotes[viewHolder.adapterPosition].text)
            listener.onViewClicked(allDiaryNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val currentItem = allDiaryNotes[position]
        holder.dateTextView.text = currentItem.date.toString()
        holder.headingTextView.text = currentItem.heading
    }

    override fun getItemCount(): Int {
        return allDiaryNotes.size
    }

    fun updateItems(list: List<DiaryContent>){
        allDiaryNotes.clear()
        allDiaryNotes.addAll(list)

        notifyDataSetChanged()
    }
}

class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val headingTextView: TextView = itemView.findViewById(R.id.diaryHeading)
    val deleteButton: ImageView = itemView.findViewById(R.id.delete)
    val dateTextView: TextView = itemView.findViewById(R.id.diaryDate)
}

interface ClickListener{
    fun onItemClicked(diaryContent: DiaryContent)
    fun onViewClicked(diaryContent: DiaryContent)
}