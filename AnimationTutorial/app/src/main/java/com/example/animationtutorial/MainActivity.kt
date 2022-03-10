package com.example.animationtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val rotate = findViewById<Button>(R.id.rotation)
        val scale = findViewById<Button>(R.id.scale)
        val transition = findViewById<Button>(R.id.translate)
        val alpha = findViewById<Button>(R.id.alpha)

        transition.setOnClickListener{
            val animationMove: Animation = AnimationUtils.loadAnimation(this, R.anim.translate)
            textView.startAnimation(animationMove)
        }

        rotate.setOnClickListener{
            val animationMove: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
            textView.startAnimation(animationMove)
        }

        scale.setOnClickListener {
            val animationMove: Animation = AnimationUtils.loadAnimation(this, R.anim.scale)
            textView.startAnimation(animationMove)
        }

        alpha.setOnClickListener {
            val animationMove: Animation = AnimationUtils.loadAnimation(this, R.anim.alpha)
            textView.startAnimation(animationMove)
        }
    }
}