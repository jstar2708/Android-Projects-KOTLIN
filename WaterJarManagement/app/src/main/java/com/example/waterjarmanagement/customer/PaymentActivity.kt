package com.example.waterjarmanagement.customer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.customer.view_models.PaymentViewModel
import com.example.waterjarmanagement.databinding.ActivityPaymentBinding
import com.google.android.material.snackbar.Snackbar
import com.razorpay.PaymentResultListener

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var paymentViewModel: PaymentViewModel
    private var mResult = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        paymentViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[PaymentViewModel::class.java]

        paymentViewModel.quantity = intent.getIntExtra("quantity", 0)
        paymentViewModel.amount = intent.getIntExtra("priceForJar", 0)
        mResult = intent.getIntExtra("type", 0)

        binding.header.text = "Payment\n₹ " + paymentViewModel.amount*paymentViewModel.quantity

        paymentViewModel.getCustomer()
        paymentViewModel.getSeller(intent.getStringExtra("sellerId").toString())

        paymentViewModel.customer.observe(this, Observer {
            setCustomerUI()
        })

        paymentViewModel.seller.observe(this, Observer {
            setSellerUI()
        })


        binding.payUsingRazorPay.setOnClickListener {
            paymentViewModel.startPayment(this)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomerUI() {
        binding.customerName.text = paymentViewModel.customer.value?.getName()
        binding.address.text = paymentViewModel.customer.value?.getAddress()
        binding.amountToPay.text = "₹ " + (paymentViewModel.quantity * paymentViewModel.amount).toString()
        binding.jarQuantity.text = paymentViewModel.quantity.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setSellerUI() {
        binding.ownerName.text = paymentViewModel.seller.value?.getOwnerName()
        binding.companyName.text = paymentViewModel.seller.value?.getCompanyName()
        binding.contactNumber.text = "+91 " + paymentViewModel.seller.value?.getCustomerCareNumber()
        binding.priceForOneJar.text = paymentViewModel.seller.value?.getJarPrice().toString()
    }

    override fun onPaymentSuccess(p0: String?) {
        binding.success.visibility = View.VISIBLE
        binding.ll2.visibility = View.GONE
        binding.success.playAnimation()
        if(mResult == 0){
            setResult(1)
        }
        else{
            setResult(2)
        }
        Snackbar.make(binding.root, "Wait for 5 seconds", 4000).show()
        binding.payUsingRazorPay.isClickable = false
        Handler().postDelayed({
            finish()
        }, 5000)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        binding.failure.visibility = View.VISIBLE
        binding.ll2.visibility = View.GONE
        binding.failure.playAnimation()
        setResult(3)
        Snackbar.make(binding.root, "Wait for 5 seconds", 4000).show()
        binding.payUsingRazorPay.isClickable = false
        Handler().postDelayed({
            finish()
        }, 5000)
    }


}