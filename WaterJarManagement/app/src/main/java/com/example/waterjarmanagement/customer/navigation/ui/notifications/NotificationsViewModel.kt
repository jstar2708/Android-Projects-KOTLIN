package com.example.waterjarmanagement.customer.navigation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsViewModel : ViewModel() {

    var customer: MutableLiveData<Customer> = MutableLiveData()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var toast: MutableLiveData<Int> = MutableLiveData(0)

    fun getCustomer(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                customer.value = snapshot.getValue(Customer::class.java)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun setCustomer(){
        database.reference.child("Customer").child(customer.value?.getUserId().toString()).setValue(customer.value).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 1
            }
            else{
                toast.value = 2
            }
        }

    }

    fun signOut() {
        auth.signOut()
    }

}