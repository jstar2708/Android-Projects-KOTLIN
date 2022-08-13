package com.example.firstchat2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstchat2.Models.User
import com.example.firstchat2.NavigationActivity
import com.example.firstchat2.R

class ChatsAdapter(private val context: Context, private val listener: UserItemOnClick): RecyclerView.Adapter<Chats>() {

    private val list: ArrayList<User> = ArrayList()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chats {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)
        val holder = Chats(view)
        view.setOnClickListener {
            listener.onClickUser(list[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: Chats, position: Int) {
        holder.nameView.text = list[position].getUserName()
        holder.lastMessageView.text = list[position].getLastMessage()

        if(list[position].getProfilePic().isEmpty()){
            holder.profilePicView.setImageResource(R.drawable.man)
        }
        else{
            Glide.with(context).load(list[position].getProfilePic()).into(holder.profilePicView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public fun updatedList(temp: ArrayList<User>){
        list.clear()

        list.addAll(temp)

        notifyDataSetChanged()
    }
}

class Chats(itemView: View): RecyclerView.ViewHolder(itemView) {
    val profilePicView: ImageView = itemView.findViewById(R.id.profilePicChats)
    val nameView: TextView = itemView.findViewById(R.id.nameChats)
    val lastMessageView: TextView = itemView.findViewById(R.id.lastMessageChats)
}

interface UserItemOnClick{
    fun onClickUser(user: User)
}