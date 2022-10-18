package com.example.waterjarmanagement

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.example.waterjarmanagement.databinding.ActivityLoginBinding
import com.example.waterjarmanagement.seller.navigation.SellerNavigationActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Checking user status")
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[LoginViewModel::class.java]
        loginViewModel.action.observe(this, Observer {
            if(it != null){
                when(it){
                    3-> progressDialog.dismiss()
                    4-> progressDialog.show()
                    else->handleAction(it)
                }
            }
        })
        loginViewModel.isUserLoggedIn(this)

        binding.loginButton.setOnClickListener {
            if(binding.emailEditText.editText?.text.isNullOrEmpty() || binding.passwordEditText.editText?.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Please fill all details correctly", Snackbar.LENGTH_LONG).show()
            }
            else{
                loginViewModel.signIn(this, binding.emailEditText.editText?.text.toString(), binding.passwordEditText.editText?.text.toString())
            }

        }

        binding.newToApp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleAction(value: Int) {
        val intent: Intent = when(value){
            1-> Intent(this, CustomerNavigationActivity::class.java)
            else-> Intent(this, SellerNavigationActivity::class.java)
        }
        loginViewModel.action.value = 3
        startActivity(intent)
        finish()
    }
}