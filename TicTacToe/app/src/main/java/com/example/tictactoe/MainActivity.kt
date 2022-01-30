package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Player 1 name
        val p1Name : EditText = findViewById(R.id.p1_edittext)
        //Player 2 name
        val p2Name : EditText = findViewById(R.id.p2_edittext)

        val continueButton : Button = findViewById(R.id.continue_button)

        //Moving to Tic_Tac_Toe activity when Continue button is clicked.
        continueButton.setOnClickListener {
            val intent: Intent = Intent(this, TicTacToe_Activity::class.java)
            intent.putExtra("p1name", p1Name.text.toString())
            intent.putExtra("p2name", p2Name.text.toString())
            startActivity(intent)
        }
    }
}