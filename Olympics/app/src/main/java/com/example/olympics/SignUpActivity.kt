package com.example.olympics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.olympics.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import models.Users

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.signUpButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.emailEditText.text.toString()
                , binding.passwordEditText.text.toString()).addOnCompleteListener { task->

                if(task.isSuccessful){

                    val users = Users(binding.userNameEditText.text.toString(), binding.emailEditText.text.toString()
                            , binding.passwordEditText.text.toString())

                    users.setUserId(task.result?.user?.uid.toString())

                    database.reference.child("Users").child(users.getUserId()).setValue(users)

                    Toast.makeText(this, "Signed up successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}