package com.example.streetlight.authentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.streetlight.NavigationActivity
import com.example.streetlight.databinding.ActivityOtpsignUpBinding
import com.google.firebase.auth.FirebaseAuth

class OtpSignUp : AppCompatActivity() {

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

        //Getting an instance of the firebase authentication
        auth = FirebaseAuth.getInstance()

        //Checking if user if already signed in the app or not, If yes then no need to login again.
        if(auth.currentUser != null){
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.generateOtp.setOnClickListener {

            if(binding.radioAge.checkedRadioButtonId == -1){
                Toast.makeText(applicationContext, "Please select your gender", Toast.LENGTH_SHORT).show()
            }

            else if(binding.name.text.isNullOrEmpty() || binding.phoneNumberEdittext.text.isNullOrEmpty()){
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, ProcessOtp::class.java)

                intent.putExtra("number", "+91 "+binding.phoneNumberEdittext.text.toString().replace(" ", ""))
                intent.putExtra("name", binding.name.text.toString())
                intent.putExtra("gender", findViewById<RadioButton>(binding.radioAge.checkedRadioButtonId).text.toString())
                intent.putExtra("password", binding.password.text.toString())

                startActivity(intent)
                finish()
            }
        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            val intent = Intent(this, OtpLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}