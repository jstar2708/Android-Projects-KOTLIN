package com.example.streetlight.authentication

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.streetlight.NavigationActivity
import com.example.streetlight.databinding.ActivityProcessOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class ProcessOtp : AppCompatActivity() {

    //Binding variable for the activity
    private lateinit var binding: ActivityProcessOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var phoneNumber: String
    private lateinit var name: String
    private lateinit var gender: String
    private lateinit var password: String
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
        gender = intent.getStringExtra("gender").toString()
        password = intent.getStringExtra("password").toString()

        Log.e("", phoneNumber)

        initiateOtp()

        binding.verifyButton.setOnClickListener {
            if(binding.enteredOtp.text.isNullOrEmpty()){
                Toast.makeText(this, "Blank field cannot be processed", Toast.LENGTH_SHORT).show()
            }
            else if(binding.enteredOtp.text!!.length != 6){
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
                    Toast.makeText(applicationContext, "Code sent", Toast.LENGTH_SHORT).show()
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

                    var isPasswordCorrect = false
                    if(gender != "null"){
                        val user: User = User(name, phoneNumber, gender, password)
                        user.setUserId(auth.currentUser?.uid.toString())
                        database.reference.child("Users").child(user.getUserId()).setValue(user)

                        Toast.makeText(this, "correct OTP", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        database.reference.child("Users").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(password.equals(snapshot.getValue(User::class.java)?.getPassword())){
                                    Toast.makeText(applicationContext, "correct OTP", Toast.LENGTH_SHORT).show()
                                    Toast.makeText(applicationContext, "Correct password", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()

                                    val intent = Intent(this@ProcessOtp, NavigationActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    dialog.dismiss()
                                    auth.signOut()
                                    Toast.makeText(applicationContext, "incorrect password", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })
                    }



                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    // Update UI
                }
            }
    }
}