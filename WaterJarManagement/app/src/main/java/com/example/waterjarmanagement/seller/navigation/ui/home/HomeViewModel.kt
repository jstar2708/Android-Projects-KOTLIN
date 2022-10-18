package com.example.waterjarmanagement.seller.navigation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.seller.model.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    public val seller: MutableLiveData<Seller> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var toast: MutableLiveData<Int> = MutableLiveData(0)

    fun getSeller(){
        database.reference.child("Seller").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                        seller.value = snapshot.getValue(Seller::class.java)!!
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("DEBUG", error.message)
            }
        }
        )
    }

    fun getMonthlyOrders(): Int{
        var count = 0
        database.reference.child("Seller").child(seller.value?.getUserId().toString()).child("Orders").child("Monthly").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                count = snapshot.childrenCount.toInt()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return count
    }

    fun getPayGoOrders(): Int{
        var count = 0
        database.reference.child("Seller").child(seller.value?.getUserId().toString()).child("Orders").child("Monthly").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                count = snapshot.childrenCount.toInt()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return count
    }
}