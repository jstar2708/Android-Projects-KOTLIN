package com.example.waterjarmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.waterjarmanagement.customer.RegisterCustomerActivity
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.example.waterjarmanagement.databinding.ActivityMainBinding
import com.example.waterjarmanagement.seller.RegisterSellerActivity
import com.example.waterjarmanagement.seller.navigation.SellerNavigationActivity
import com.example.waterjarmanagement.seller.model.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.customer.setOnClickListener {
            val intent = Intent(this, RegisterCustomerActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.seller.setOnClickListener {
            val intent = Intent(this, RegisterSellerActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}