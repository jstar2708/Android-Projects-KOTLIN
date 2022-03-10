package com.example.ktichenguide

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val appName = findViewById<ImageView>(R.id.homemade)
        val animation = AnimationUtils.loadAnimation(this, R.anim.alpha)
        appName.startAnimation(animation)
        Handler().postDelayed(Runnable {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}
