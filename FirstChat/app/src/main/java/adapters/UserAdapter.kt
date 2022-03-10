package adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstchat.R
import models.Users

class UserAdapter(private val context: Context?, private val listener: UserItemOnClick): RecyclerView.Adapter<UserViewHolder>() {

    private val userList = ArrayList<Users>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_item_view, parent, false)
        val viewHolder = UserViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(userList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        if (context != null) {
            Glide.with(context).load(currentUser.getProfilePic()).placeholder(R.drawable.man).into(holder.profilePic)
        }
        holder.name.text = currentUser.getUserName()
        holder.message.text = currentUser.getLastMessage()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateListItem(list: ArrayList<Users>){
        userList.clear()
        userList.addAll(list)

        notifyDataSetChanged()
    }
}

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val profilePic: ImageView = itemView.findViewById(R.id.profilePic)
    val name: TextView = itemView.findViewById(R.id.name)
    val message: TextView = itemView.findViewById(R.id.lastMessage)
}

interface UserItemOnClick{
    fun onItemClicked(users: Users)
}