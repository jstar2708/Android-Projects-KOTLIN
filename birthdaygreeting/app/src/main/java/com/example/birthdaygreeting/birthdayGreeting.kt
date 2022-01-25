package com.example.birthdaygreeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class birthdayGreeting : AppCompatActivity() {

    companion object{
        const val NAME_EXTRA = "name_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday_greeting)

        val intent : Intent = getIntent()
        val name = intent.getStringExtra(NAME_EXTRA)

        val birthDayText = findViewById<TextView>(R.id.birthday_textview)
        birthDayText.text = "Happy Birthday \n$name"
    }
}