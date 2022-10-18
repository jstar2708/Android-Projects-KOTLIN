package com.example.waterjarmanagement.customer.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.models.MonthlyOrder
import com.example.waterjarmanagement.customer.models.PayGo
import com.example.waterjarmanagement.seller.model.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderViewModel: ViewModel() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var seller: MutableLiveData<Seller> = MutableLiveData()
    var customer: MutableLiveData<Customer> = MutableLiveData()
    var toast: MutableLiveData<Int> = MutableLiveData(0)
    var quantity = 0


    fun orderMonthlyOrder(){
        val orderId = database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("Monthly").push().key.toString()

        val monthlyOrder = MonthlyOrder(30,
            seller.value?.getUserId().toString(),
            false,
            seller.value?.getJarPrice()!!.toInt(),
            seller.value?.getOwnerName().toString(),
            customer.value?.getUserId().toString(),
            orderId)

        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("Monthly").child(orderId).setValue(monthlyOrder)

        database.reference.child("Seller").child(seller.value?.getUserId().toString()).child("Orders").child("Monthly").child(orderId).setValue(monthlyOrder).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 1
            }
            else{
                toast.value = 2
            }
        }
    }

    fun orderPayGoOrder(){
        val orderId = database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("PayGo").push().key.toString()

        val payGo = PayGo(quantity,
            seller.value?.getUserId().toString(),
            false,
            seller.value?.getJarPrice()!!.toInt(),
            seller.value?.getOwnerName().toString(),
            customer.value?.getUserId().toString(),
            orderId)

        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Orders").child("PayGo").child(orderId).setValue(payGo)

        database.reference.child("Seller").child(seller.value?.getUserId().toString()).child("Orders").child("PayGo").child(orderId).setValue(payGo).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 1
            }
            else{
                toast.value = 2
            }
        }
    }
    fun getSeller(sellerId: String){
        database.reference.child("Seller").child(sellerId).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                seller.value = snapshot.getValue(Seller::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                toast.value = 1
            }

        })
    }

    fun getCustomer(){
        val customerId = auth.currentUser?.uid.toString()
        database.reference.child("Customer").child(customerId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                customer.value = snapshot.getValue(Customer::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                toast.value = 2
            }

        })
    }
}