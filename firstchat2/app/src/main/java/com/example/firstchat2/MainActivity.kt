package com.example.firstchat2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.firstchat2.Models.Message
import com.example.firstchat2.adapters.InboxAdapter
import com.example.firstchat2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var senderId: String
    private lateinit var recieverId: String
    private lateinit var recieverProfilePic: String
    private lateinit var recieverUserName: String
    private lateinit var senderRoom: String
    private lateinit var recieverRoom: String
    private lateinit var progressBar: ProgressBar
    private var messageList: ArrayList<com.example.firstchat2.Models.Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://firstchat-86bd2-default-rtdb.asia-southeast1.firebasedatabase.app")

        senderId = auth.currentUser?.uid.toString()
        recieverId = intent.getStringExtra("userId").toString()
        recieverProfilePic = intent.getStringExtra("profilePic").toString()
        recieverUserName = intent.getStringExtra("userName").toString()

        senderRoom = senderId + recieverId
        recieverRoom = recieverId + senderId

        Glide.with(this).load(recieverProfilePic).placeholder(R.drawable.man).into(binding.inboxProfilePic)
        binding.inboxName.text = recieverUserName

        binding.inboxRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = InboxAdapter(this)
        binding.inboxRecyclerView.adapter = adapter

        binding.arrow.setOnClickListener {
            onBackPressed()
            finish()
        }

        database.reference.child("Chats").child(senderRoom).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                progressBar.visibility = View.VISIBLE
                messageList.clear()
                snapshot.children.forEach {
                    it.getValue(Message::class.java)?.let { it1 -> messageList.add(it1) }
                }
                progressBar.visibility = View.INVISIBLE
                adapter.updateMessage(messageList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error in connecting to database", Toast.LENGTH_SHORT).show()
            }
        })
        binding.inboxSendButton.setOnClickListener {
            if (!binding.inboxEditText.text.isNullOrEmpty()) {
                val message: com.example.firstchat2.Models.Message =
                    com.example.firstchat2.Models.Message(
                        senderId,
                        binding.inboxEditText.text.toString(),
                        Date().time
                    )
                database.reference.child("Chats").child(senderRoom).push().setValue(message).addOnSuccessListener {

                    database.reference.child("Chats").child(recieverRoom).push().setValue(message)
                        .addOnSuccessListener {

                        }
                }
            }
            binding.inboxEditText.text.clear()
        }
    }
}