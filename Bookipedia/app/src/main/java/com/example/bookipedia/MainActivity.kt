package com.example.bookipedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topic : EditText = findViewById(R.id.search_edit_view)

        val searchButton : Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener(View.OnClickListener {
            if(topic.text.toString() == ""){
                Toast.makeText(this, "Please enter a topic!", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent : Intent = Intent(this, BookList::class.java)
                intent.putExtra("topic", topic.text.toString())
                startActivity(intent)
            }
        })
    }
}