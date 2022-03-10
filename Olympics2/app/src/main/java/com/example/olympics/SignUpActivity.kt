package com.example.olympics

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.olympics.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import models.Users

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating account")
        progressDialog.setMessage("We are creating your account")

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://olympics-53b30-default-rtdb.asia-southeast1.firebasedatabase.app/")

        binding.signUpButton.setOnClickListener {
            progressDialog.show()
            auth.createUserWithEmailAndPassword(binding.emailEditText.text.toString()
                , binding.passwordEditText.text.toString()).addOnCompleteListener { task->

                if(task.isSuccessful){
                    progressDialog.dismiss()
                    val users = Users(binding.nameEditText.text.toString(), binding.emailEditText.text.toString()
                        , binding.passwordEditText.text.toString())

                    users.setUserId(task.result?.user?.uid.toString())

                    database.reference.child("Users").child(users.getUserId()).setValue(users)

                    Toast.makeText(this, "Signed up successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    progressDialog.dismiss()
                    Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.alreadyHaveAnAcc.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}