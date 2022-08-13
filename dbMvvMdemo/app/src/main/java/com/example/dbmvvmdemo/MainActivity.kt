package com.example.dbmvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dbmvvmdemo.databinding.ActivityMainBinding
import com.example.dbmvvmdemo.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        lifecycle.addObserver(Observer())
        val p: Product = Product("Love")
        dataBinding.product = p
//        val t1 = findViewById<TextView>(R.id.t1)
//        t1.text = "Hey"    This will not have any effect

        val mainViewModel: MainViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
        dataBinding.product = mainViewModel.getProduct()
    }
}