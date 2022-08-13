package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private var allContacts: List<Contact>, private val listener: ContactItemClicked): RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
        val holder = ContactViewHolder(view)
        holder.callButton.setOnClickListener {
            listener.onCallButtonClicked(allContacts[holder.adapterPosition])
        }
        holder.deleteButton.setOnClickListener {
            listener.onDeleteButtonClicked(allContacts[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = allContacts[position]
        holder.name.text = contact.name
        if(contact.gender == "Male"){
            holder.profilePic.setImageResource(R.drawable.man)
        }
        else{
            holder.profilePic.setImageResource(R.drawable.woman)
        }

    }

    override fun getItemCount(): Int {
        return allContacts.size
    }

    fun updateList(list: List<Contact>){
        allContacts = list

        notifyDataSetChanged()
    }
}

class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val profilePic: ImageView = itemView.findViewById(R.id.profilePic)
    val name: TextView = itemView.findViewById(R.id.name)
    val callButton: ImageButton = itemView.findViewById(R.id.callButton)
    val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
}