package com.example.waterjarmanagement.seller.view_models

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.MainActivity
import com.example.waterjarmanagement.customer.RegisterCustomerActivity
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.seller.RegisterSellerActivity
import com.example.waterjarmanagement.seller.model.Seller
import com.example.waterjarmanagement.seller.navigation.SellerNavigationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterSellerViewModel(application: Application): AndroidViewModel(application) {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val action: MutableLiveData<Int> = MutableLiveData()
    val toast: MutableLiveData<Int> = MutableLiveData()

    fun registerSeller(seller: Seller, isPhoneNumberVerified: Boolean){
        if(!isPhoneNumberVerified){
            toast.value = 1
            return
        }
        auth.createUserWithEmailAndPassword(seller.getEmail(), seller.getPassword()).addOnCompleteListener {
            if(it.isSuccessful){
                seller.setUserId(auth.currentUser?.uid.toString())
                toast.value = 2
                database.reference.child("Seller").child(auth.uid.toString()).setValue(seller)
                action.value = 1
            }
            else{
                toast.value = 3
                Log.d("Error", it.exception?.message.toString())
            }
        }
    }

}