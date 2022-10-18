package com.example.waterjarmanagement.customer.navigation.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.seller.model.Seller
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    val list: MutableLiveData<ArrayList<Seller>> = MutableLiveData(ArrayList())
    var toast: MutableLiveData<Int> = MutableLiveData()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")

    fun getSellers(){
        database.reference.child("Seller").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList: ArrayList<Seller> = ArrayList()
                snapshot.children.forEach {
                    it.getValue(Seller::class.java)?.let { it1 -> arrayList.add(it1) }
                }
                list.value = arrayList
            }
            override fun onCancelled(error: DatabaseError) {
                toast.value = 1
                Log.d("ERROR", error.details)
            }
        })
    }

    fun sortBy(value: Int) {
        val arrayList: ArrayList<Seller> = ArrayList()
        list.value?.let { arrayList.addAll(it) }
        if(value == 0){
            arrayList.sortByDescending {it.getJarPrice()}
        }
        else {
            arrayList.sortBy {it.getJarPrice()}
        }
        list.value = arrayList
    }

    fun sortByRating(value: Int){
        val arrayList: ArrayList<Seller> = ArrayList()
        list.value?.let { arrayList.addAll(it) }
        if(value == 0){
            arrayList.sortBy { it.getRating() }
        }
        else{
            arrayList.sortByDescending { it.getRating() }
        }

        list.value = arrayList
    }

    fun sortByCustomers() {
        val arrayList: ArrayList<Seller> = ArrayList()
        list.value?.let { arrayList.addAll(it) }
        arrayList.sortBy { it.getCustomerCount() }
        list.value = arrayList
    }
}