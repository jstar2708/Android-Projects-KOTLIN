package com.example.birthdaygreeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name : EditText = findViewById(R.id.nameinput)

        val button : Button = findViewById(R.id.create_birthday_button)
        button.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(this, birthdayGreeting::class.java)
                intent.putExtra(birthdayGreeting.NAME_EXTRA, name.text.toString());
                startActivity(intent)
        })

    }

    fun onClick(view : View){
//        val intent : Intent = Intent(this, birthdayGreeting::class.java)
//        startActivity(intent)
    }
}