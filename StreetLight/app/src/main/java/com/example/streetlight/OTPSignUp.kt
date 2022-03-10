package com.example.streetlight

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.streetlight.databinding.ActivityOtpsignUpBinding
import com.google.firebase.auth.FirebaseAuth

class OTPSignUp : AppCompatActivity() {

    //Binding variable for the activity
    private lateinit var binding: ActivityOtpsignUpBinding
    //Firebase Authentication reference variable
    private lateinit var auth: FirebaseAuth

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflating the view
        binding = ActivityOtpsignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = ProgressDialog(this)
        dialog.setTitle("Wait")
        dialog.setMessage("Logging you in...")

        //Getting an instance of the firebase authentication
        auth = FirebaseAuth.getInstance()

        //Checking if user if already signed in the app or not, If yes then no need to login again.
        if(auth.currentUser != null){
            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.nextButton.setOnClickListener {

            dialog.show()

            if(binding.name.text.isNullOrEmpty() || binding.phoneNumberEdittext.text.isNullOrEmpty()){
                dialog.dismiss()
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.dismiss()
                val intent = Intent(this, ProcessOtp::class.java)
                intent.putExtra("number", "+91 "+binding.phoneNumberEdittext.text.toString().replace(" ", ""))
                intent.putExtra("name", binding.name.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}