package com.example.waterjarmanagement

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.example.waterjarmanagement.seller.model.Seller
import com.example.waterjarmanagement.seller.navigation.SellerNavigationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val action: MutableLiveData<Int> = MutableLiveData()

    fun signIn(context: Context, email: String, password: String){

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                sellerOrCustomer(context)
            }
            else{
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isUserLoggedIn(context: Context){
        if(auth.currentUser != null){
            action.value = 4
            sellerOrCustomer(context)
        }
        else{
            action.value = 3
        }
    }

    private fun sellerOrCustomer(context: Context){
        database.reference.child("Customer").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if(it.getValue(Customer::class.java)?.getUserId() == auth.currentUser!!.uid){
                        action.value = 1
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        database.reference.child("Seller").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if(it.getValue(Seller::class.java)?.getUserId() == auth.currentUser!!.uid){
                        action.value = 2
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}