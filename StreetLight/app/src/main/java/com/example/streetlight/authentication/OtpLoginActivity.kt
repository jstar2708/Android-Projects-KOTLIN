package com.example.streetlight.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.streetlight.R
import com.example.streetlight.databinding.ActivityOtpLoginBinding

class OtpLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.login.setOnClickListener {

            val phoneNumber = binding.phoneNumberEdittext.text.toString()
            val password = binding.password.text.toString()
            if(phoneNumber.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(applicationContext, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, ProcessOtp::class.java)
                intent.putExtra("number", "+91 " + phoneNumber.replace(" ", ""))
                intent.putExtra("password", password)
                startActivity(intent)
                finish()
            }
        }

        binding.goForSignUp.setOnClickListener {
            val intent = Intent(this, OtpSignUp::class.java)
            startActivity(intent)
            finish()
        }
    }
}