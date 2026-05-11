package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CakeBuilderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. THE GATEKEEPER: Check if a baker is already logged in
        val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("BAKERY_NAME", null)

        if (savedName != null) {
            // If the name exists, SKIP LOGIN and jump straight to the Welcome/Game screen
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish() // This ensures they can't "back button" into the login screen
            return // Exit this function so we don't load the login layout
        }

        // 2. THE LOGIN SCREEN: Only shows if savedName was null
        setContentView(R.layout.activity_cake_builder)

        val etBakeryName = findViewById<EditText>(R.id.etUsername)
        val etSecretKey = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val bakeryName = etBakeryName.text.toString()
            val secretKey = etSecretKey.text.toString()

            if (bakeryName.isNotEmpty() && secretKey.isNotEmpty()) {
                // SAVE TO THE "NOTEBOOK" (SharedPreferences)
                val editor = sharedPref.edit()
                editor.putString("BAKERY_NAME", bakeryName)
                editor.apply()

                Toast.makeText(this, "Welcome, $bakeryName!", Toast.LENGTH_SHORT).show()

                // MOVE TO THE WELCOME PAGE
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
                finish() // Login complete, close this screen!

            } else {
                Toast.makeText(this, "Please name your bakery!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}