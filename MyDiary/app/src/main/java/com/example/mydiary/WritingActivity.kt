package com.example.mydiary

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

class WritingActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var heading: String
    private lateinit var content: String
    private lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = intent
        if(intent.getStringExtra("text").isNullOrEmpty()){

            setContentView(R.layout.activity_writing)

            showDialogBox()

            editText = findViewById(R.id.diaryContent)
            val submitButton: Button = findViewById(R.id.submit)

            submitButton.setOnClickListener {
                content = editText.text.toString()
                date = Date().toString()
                intent = Intent(this, MainActivity::class.java)
                intent.putExtra("heading", heading)
                intent.putExtra("date", date)
                intent.putExtra("content", content)
                Log.e("", "$heading  $date  $content" )
                setResult(-1, intent)
                finish()
            }
        }
        else{
            setContentView(R.layout.content_view)
            val editText: EditText = findViewById(R.id.viewContent)
            editText.setText(intent.getStringExtra("text"))
        }

    }

    private fun showDialogBox(){
        val dialogBuilder = AlertDialog.Builder(this)
        val editTextLayout = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, false)
        val editText = editTextLayout.findViewById<EditText>(R.id.editText)

        with(dialogBuilder){
            setTitle("Enter title")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                heading = editText.text.toString()
            })

            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            dialogBuilder.setView(editTextLayout)
            show()
        }
    }
}