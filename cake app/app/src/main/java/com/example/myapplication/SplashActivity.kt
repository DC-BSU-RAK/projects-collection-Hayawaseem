package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This must match your XML file name
        setContentView(R.layout.activity_splash2)

        // 1. Find the cake image using the ID from your XML
        val cakeLogo = findViewById<ImageView>(R.id.cake)

        // 2. Load the animation from your res/anim folder
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // 3. Start the animation on the cake image
        cakeLogo.startAnimation(slideUp)

        // 4. Wait 3 seconds then move to the MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}