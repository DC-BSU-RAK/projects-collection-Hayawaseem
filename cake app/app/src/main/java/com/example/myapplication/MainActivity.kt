package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Sets the layout for the Welcome Screen
        setContentView(R.layout.activity_main)

        // 2. Initialize Views using the correct IDs from your XML
        val customizeBtn = findViewById<MaterialButton>(R.id.btnGoToCustomizer)
        val instructionsBtn = findViewById<MaterialButton>(R.id.btnInstructions)
        val welcomeTitle = findViewById<TextView>(R.id.welcomeTitle)

        // --- ANIMATION LOGIC ---
        // Loads the fade and slide-up effect for the buttons
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.fade_slide_up)

        // Starts the animation as soon as the activity loads
        customizeBtn?.startAnimation(slideUp)
        instructionsBtn?.startAnimation(slideUp)
        // -----------------------

        // 3. Navigation Logic to the next screen (e.g., Login or Customizer)
        customizeBtn?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 4. Instructions Dialog Logic
        instructionsBtn?.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_instructions)

            // Makes the dialog background transparent to fit your cream aesthetic
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            // Logic for the Done button inside the instructions popup
            val doneBtn = dialog.findViewById<MaterialButton>(R.id.btnDone)
            doneBtn?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}