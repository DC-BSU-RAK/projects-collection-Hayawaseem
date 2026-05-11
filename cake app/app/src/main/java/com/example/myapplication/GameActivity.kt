package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // 1. Fetch the name using the "USER_NAME" key (matching your login logic)
        val sharedPref = getSharedPreferences("BakeryData", MODE_PRIVATE)
        val savedName = sharedPref.getString("USER_NAME", "Baker")

        // 2. Updated ID to 'welcomeTitle' to match your newest XML theme
        // If your XML still uses 'tvWelcomeMessage', change this ID back to that.
        val tvWelcome = findViewById<TextView>(R.id.welcomeTitle)
        tvWelcome.text = "Welcome, Baker $savedName!"

        // --- WAIT 2 SECONDS THEN MOVE TO BASE SELECTION ---
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, BaseSelectionActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}