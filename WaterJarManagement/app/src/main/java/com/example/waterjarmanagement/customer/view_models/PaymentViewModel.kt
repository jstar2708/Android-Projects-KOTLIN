package com.example.waterjarmanagement.customer.view_models

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.customer.PaymentActivity
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.seller.model.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.razorpay.Checkout
import org.json.JSONObject

class PaymentViewModel: ViewModel() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var seller: MutableLiveData<Seller> = MutableLiveData()
    var customer: MutableLiveData<Customer> = MutableLiveData()
    var toast: MutableLiveData<Int> = MutableLiveData(0)
    var quantity: Int = 0
    var amount: Int = 0

    fun startPayment(paymentActivity: PaymentActivity) {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = paymentActivity
        val co = Checkout()
        co.setKeyID("rzp_test_qa600t0v1N22oa")
        co.setImage(R.drawable.seller)
        try {
            val options = JSONObject()
            options.put("name",seller.value?.getCompanyName())
            options.put("description","Jar order")
            //You can omit the image option to fetch the image from dashboard
            //options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#00E5FF");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount",amount*quantity*100)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email",customer.value?.getName())
            prefill.put("contact",customer.value?.getPhoneNumber())

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun getSeller(sellerId: String){
        database.reference.child("Seller").child(sellerId).addValueEventListener(object: ValueEventListener{
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
        database.reference.child("Customer").child(customerId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                customer.value = snapshot.getValue(Customer::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                toast.value = 2
            }

        })
    }



}