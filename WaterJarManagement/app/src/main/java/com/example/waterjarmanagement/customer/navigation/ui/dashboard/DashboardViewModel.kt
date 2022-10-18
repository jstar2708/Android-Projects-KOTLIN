package com.example.waterjarmanagement.customer.navigation.ui.dashboard

import android.util.Log
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.MonthlyOrder
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.models.PayGo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardViewModel : ViewModel() {
    var orderList: MutableLiveData<ArrayList<MonthlyOrder>> = MutableLiveData()
    var payGoOrderList: MutableLiveData<ArrayList<PayGo>> = MutableLiveData()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("Monthly").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList: ArrayList<MonthlyOrder> = ArrayList()
                snapshot.children.forEach {
                    it.getValue(MonthlyOrder::class.java)?.let { it1 -> arrayList.add(it1) }
                }
                orderList.value = arrayList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
            }
        })
    }

    fun getPayGoOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("PayGo").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList = ArrayList<PayGo>()
                snapshot.children.forEach {
                    arrayList.add(it.getValue(PayGo::class.java)!!)
                }
                payGoOrderList.value = arrayList

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
            }

        })
    }

    fun updateMonthlyOrder(orderId: String, sellerId: String){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("Monthly").child(orderId).setValue(null)
        database.reference.child("Seller").child(sellerId).child("Orders").child("Monthly").child(orderId).setValue(null)
    }

    fun updatePayGoOrder(orderId: String, sellerId: String){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("PayGo").child(orderId).setValue(null)
        database.reference.child("Seller").child(sellerId).child("Orders").child("PayGo").child(orderId).setValue(null)
    }
}