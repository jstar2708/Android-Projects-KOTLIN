package com.example.olympics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.olympics.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val animationTextView = AnimationUtils.loadAnimation(this, R.anim.splasholympics_textview)

        binding.olympicsTextView.startAnimation(animationTextView)

        Handler().postDelayed(Runnable {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}