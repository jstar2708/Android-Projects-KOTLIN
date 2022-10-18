package com.example.streetlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.streetlight.authentication.OtpLoginActivity
import com.example.streetlight.authentication.OtpSignUp

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, OtpSignUp::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}