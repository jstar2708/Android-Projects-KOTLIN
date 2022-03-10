package com.example.firstchat

import adapters.ChatsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.firstchat.databinding.ActivityChatsDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import fragments.ChatsFragment
import models.Messages
import java.util.*
import kotlin.collections.ArrayList

class ChatsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatsDetailBinding
    private lateinit var otherUserName: String
    private lateinit var otherUserId: String
    private lateinit var otherProfilePic: String
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var senderId: String
    private var messageList: ArrayList<Messages> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance("https://firstchat-bb8ce-default-rtdb.asia-southeast1.firebasedatabase.app")
        auth = FirebaseAuth.getInstance()

        otherProfilePic = intent.getStringExtra("profilePic").toString()
        otherUserName = intent.getStringExtra("username").toString()
        otherUserId = intent.getStringExtra("userid").toString()

        senderId = auth.currentUser?.uid.toString()

        binding.otherUserNameChats.text = otherUserName
        Glide.with(this).load(otherProfilePic).placeholder(R.drawable.woman).into(binding.profilePicChats)
        supportActionBar?.hide()

        binding.backButtonChats.setOnClickListener {
            super.onBackPressed()
        }


        binding.chatsRecyclerView.adapter = ChatsAdapter(messageList, this)
        binding.chatsRecyclerView.layoutManager = LinearLayoutManager(this)

        val senderRoom = senderId + otherUserId
        val recieverRoom = otherUserId + senderId

        binding.sendButton.setOnClickListener {
            val message: Messages = Messages(senderId, binding.chatsEditText.text.toString(), Date().time)
            binding.chatsEditText.setText("")

            database.reference.child("Chats").child(senderRoom).push().setValue(message).addOnSuccessListener {

                messageList.add(message)

                database.reference.child("Chats").child(recieverRoom).push().setValue(message).addOnSuccessListener {

                }
            }
        }
    }
}