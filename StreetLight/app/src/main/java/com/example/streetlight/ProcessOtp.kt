package com.example.streetlight

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.streetlight.databinding.ActivityProcessOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class ProcessOtp : AppCompatActivity() {

    //Binding variable for the activity
    private lateinit var binding: ActivityProcessOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var phoneNumber: String
    private lateinit var name: String
    private lateinit var otpId: String
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = ProgressDialog(this)
        dialog.setTitle("Wait")
        dialog.setMessage("Logging you in...")


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://street-light-cbd73-default-rtdb.asia-southeast1.firebasedatabase.app")
        supportActionBar?.hide()

        phoneNumber = intent.getStringExtra("number").toString()
        name = intent.getStringExtra("name").toString()

        Log.e("", phoneNumber)

        initiateOtp()

        binding.verifyButton.setOnClickListener {
            if(binding.enteredOtp.text.isNullOrEmpty()){
                Toast.makeText(this, "Blank field cannot be processed", Toast.LENGTH_SHORT).show()
            }
            else if(binding.enteredOtp.text.length != 6){
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(otpId, binding.enteredOtp.text.toString())
                signInWithPhoneAuthCredential(credential)
            }
        }

    }

    private fun initiateOtp(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    otpId = p0;
                }
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(p0)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext, p0.message.toString(), Toast.LENGTH_SHORT).show()

                }

            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val complaint = Complaint(name, phoneNumber)
                    complaint.setUid(auth.currentUser?.uid.toString())
                    database.reference.child("Users").child(complaint.getUid()).setValue(complaint)

                    Toast.makeText(this, "correct OTP", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                    }
                    // Update UI
                }
            }
    }
}