package com.example.firebasepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database("https://fir-practice-e021c-default-rtdb.asia-southeast1.firebasedatabase.app")
        myRef = database.getReference("Message")
        myRef.setValue("Hello World")
        myRef.setValue("All is well")
        myRef.setValue("Hey yea!!")
        myRef.setValue("ByeBYEW")
        val textView: TextView = findViewById(R.id.textView)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                textView.text = snapshot.getValue(String::class.java)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
}