package com.example.waterjarmanagement.customer.navigation.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.Jars
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.models.PayGo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardViewModel : ViewModel() {
    var orderList: MutableLiveData<ArrayList<Jars>> = MutableLiveData(ArrayList())
    var payGoOrderList: MutableLiveData<ArrayList<PayGo>> = MutableLiveData(ArrayList())
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var customer: Customer

    fun getOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val customer = snapshot.getValue(Customer::class.java)
                orderList.value = customer?.sellers
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
                Log.e("ERROR", error.message)
            }

        })
    }

    fun getPayGoOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                customer = snapshot.getValue(Customer::class.java)!!
                payGoOrderList.value = customer.payGoOrders
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
                Log.e("ERROR", error.message)
            }

        })
    }

    fun hello(){
        val list: ArrayList<PayGo> = ArrayList()
        list.add(PayGo(4, "6u3JFcn61RYHn9xiCmQhpvxvJrM2", false, 55, "Jar Seller 4"))
        list.add(PayGo(2, "CoCif7cB8hMD72vYmtpyBO9u9jx1", false, 45, "Jar Seller 1"))
        customer.payGoOrders = list
        database.reference.child("Customer").child(customer.getUserId()).setValue(customer)

    }
}