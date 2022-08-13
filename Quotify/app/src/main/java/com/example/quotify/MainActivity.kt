package com.example.quotify

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.quotify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var mainModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        supportActionBar?.hide()

        mainModel = ViewModelProvider(this, MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)

        var q = mainModel.getQuote()
        dataBinding.quote.text = q.text
        dataBinding.author.text = q.author

        dataBinding.nextButton.setOnClickListener{
            if(mainModel.index < mainModel.size-1){
                q = mainModel.nextQuote()
                dataBinding.quote.text = q.text
                dataBinding.author.text = q.author
            }
            else{
                Toast.makeText(this, "No more quotes", Toast.LENGTH_SHORT).show()
            }
        }

        dataBinding.previousButton.setOnClickListener {
            if(mainModel.index > 0){
                q = mainModel.previousQuote()
                dataBinding.quote.text = q.text
                dataBinding.author.text = q.author
            }
            else{
                Toast.makeText(this, "No more quotes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}