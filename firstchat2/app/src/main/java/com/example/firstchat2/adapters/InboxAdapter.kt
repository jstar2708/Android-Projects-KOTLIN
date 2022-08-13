package com.example.firstchat2.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firstchat2.Models.Message
import com.example.firstchat2.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InboxAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messageList: ArrayList<Message> = ArrayList()
    private val SENDER_MESSAGE = 1;
    private val RECIEVER_MESSAGE = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == SENDER_MESSAGE){
            val view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false)
            return SenderInboxHolder(view)
        }

        val view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false)
        return RecieverInboxHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == SenderInboxHolder::class.java){
            (holder as SenderInboxHolder).messageView.text = messageList[position].getMessage()
            holder.timeView.text = getShortDate(messageList[position].getTime())
        }
        else{
            (holder as RecieverInboxHolder).messageView.text = messageList[position].getMessage()
            holder.timeView.text = getShortDate(messageList[position].getTime())
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(messageList[position].getId() == FirebaseAuth.getInstance().currentUser?.uid){
            return SENDER_MESSAGE
        }
        return RECIEVER_MESSAGE
    }

    public fun updateMessage(list: ArrayList<Message>){
        messageList.clear()

        messageList.addAll(list)

        notifyDataSetChanged()
    }

    fun getShortDate(ts: Long): String {
        val stamp = java.sql.Timestamp(ts) // from java.sql.timestamp
        val date = Date(stamp.time)

        val sdf = SimpleDateFormat("EEE, dd-MMM,\n HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata") // set your timezone appropriately

        return sdf.format(date)
    }
}

class SenderInboxHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val messageView: TextView = itemView.findViewById(R.id.senderMessage)
    val timeView: TextView = itemView.findViewById(R.id.senderTime)
}

class RecieverInboxHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val messageView: TextView = itemView.findViewById(R.id.recieverMessage)
    val timeView: TextView = itemView.findViewById(R.id.recieverTime)
}