package com.example.firstchat

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import com.example.firstchat.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import models.Users

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    private lateinit var user: Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating account")
        progressDialog.setMessage("We are creating your account")

        auth = FirebaseAuth.getInstance()  //returns an instance
        database = FirebaseDatabase.getInstance("https://firstchat-bb8ce-default-rtdb.asia-southeast1.firebasedatabase.app")



        binding.signUpButton.setOnClickListener {
            if(!user.getGender().isNullOrEmpty()){
                progressDialog.show()
                auth.createUserWithEmailAndPassword(binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        val userId: String = task.result?.user?.uid.toString()
                        Log.e("", userId)
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
            else{
                Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show()
            }
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                user = Users(binding.userNameEditText.text.toString(), binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString())

                when(binding.genderSpinner.selectedItemPosition){
                    1-> user.setGender("Male")
                    2-> user.setGender("Female")
                    3-> user.setGender("Transgender")
                    4-> user.setGender("Prefer not to say")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.alreadyHaveAnAcc.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}