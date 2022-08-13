package com.example.waterjarmanagement.customer.view_models

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
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase

class RegisterCustomerViewModel(application: Application): AndroidViewModel(application){

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val action: MutableLiveData<Int> = MutableLiveData()
    val toast: MutableLiveData<Int> = MutableLiveData()

    fun registerCustomer(customer: Customer, isPhoneNumberVerified: Boolean){
        if(!isPhoneNumberVerified){
            toast.value = 1
            return
        }
        auth.createUserWithEmailAndPassword(customer.getEmail(), customer.getPassword()).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 2
                customer.setUserId(auth.currentUser?.uid.toString())
                database.reference.child("Customer").child(auth.uid.toString()).setValue(customer)
                action.value = 1
            }
            else{
                toast.value = 3
                Log.d("Error", it.exception?.message.toString())
            }
        }
    }


}