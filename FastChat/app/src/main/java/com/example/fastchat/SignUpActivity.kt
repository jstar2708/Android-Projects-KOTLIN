package com.example.fastchat

import Models.Users
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.fastchat.databinding.ActivitySignInBinding
import com.example.fastchat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating account")
        progressDialog.setMessage("We are creating your account")

        auth = FirebaseAuth.getInstance()  //returns an instance
        database = Firebase.database


        binding.signUpButton.setOnClickListener {
            progressDialog.show()
            auth.createUserWithEmailAndPassword(binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()).addOnCompleteListener {task->
                if(task.isSuccessful){
                    val user: Users = Users(binding.userNameEditText.text.toString(), binding.emailEditText.text.toString(),
                                                        binding.passwordEditText.text.toString())

                    val userId: String = task.result.user?.uid.toString()
                    user.setUserId(userId)
                    database.reference.child("Users").child(userId).setValue(user)
                    progressDialog.dismiss()
                    Toast.makeText(this, "User successfully registered", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    progressDialog.dismiss()
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
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