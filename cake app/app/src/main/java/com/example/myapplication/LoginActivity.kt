package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Find the heading text
        // Note: Ensure your XML has android:id="@+id/txtBakerLogin"
        val loginTitle = findViewById<TextView>(R.id.txtBakerLogin)

        // 2. Load the animation from res/anim/header_animation.xml
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.header_animation)

        // 3. Start the transition
        loginTitle.startAnimation(myAnim)

        val editUser = findViewById<EditText>(R.id.editUsername)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val name = editUser.text.toString()
            if (name.isNotEmpty()) {
                val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
                sharedPref.edit().putString("USER_NAME", name).apply()

                // GO TO CAKE SELECTION
                val intent = Intent(this, BaseSelectionActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Enter your name, Baker!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

