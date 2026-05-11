package com.example.moodcalculator

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hides the top title bar
        supportActionBar?.hide()

        // 1. Link the Layout Layers
        val homeLayout = findViewById<LinearLayout>(R.id.homeLayout)
        val questionLayout = findViewById<LinearLayout>(R.id.questionLayout)
        val resultLayout = findViewById<LinearLayout>(R.id.resultLayout)

        // 2. Link Interactive Elements
        val btnStart = findViewById<MaterialButton>(R.id.btnStart)
        val btnHappy = findViewById<MaterialButton>(R.id.btnHappy)
        val btnSad = findViewById<MaterialButton>(R.id.btnSad)
        val btnAnxious = findViewById<MaterialButton>(R.id.btnAnxious)
        val btnMotivated = findViewById<MaterialButton>(R.id.btnMotivated)
        val btnRelaxed = findViewById<MaterialButton>(R.id.btnRelaxed)
        val btnBored = findViewById<MaterialButton>(R.id.btnBored)
        val btnReset = findViewById<MaterialButton>(R.id.btnReset)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        // 3. Load the Fade-in Animation
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // --- NAVIGATION ---

        btnStart.setOnClickListener {
            homeLayout.visibility = View.GONE
            questionLayout.visibility = View.VISIBLE
            // Triggers the animation you created in res/anim/fade_in.xml
            questionLayout.startAnimation(fadeInAnim)
        }

        // --- ADVICE LOGIC ---

        btnHappy.setOnClickListener {
            showAdvice("You're doing great! Keep up that positive energy.", questionLayout, resultLayout, txtResult)
        }

        btnSad.setOnClickListener {
            showAdvice("It's okay to feel down. Take some time for yourself today.", questionLayout, resultLayout, txtResult)
        }

        btnAnxious.setOnClickListener {
            showAdvice("Take a deep breath. You've handled tough days before.", questionLayout, resultLayout, txtResult)
        }

        btnMotivated.setOnClickListener {
            showAdvice("That's the spirit! Use this momentum to tackle your goals.", questionLayout, resultLayout, txtResult)
        }

        btnRelaxed.setOnClickListener {
            showAdvice("Enjoy this peace. It's the perfect time to recharge.", questionLayout, resultLayout, txtResult)
        }

        btnBored.setOnClickListener {
            showAdvice("Boredom is a spark for creativity. Try something new today!", questionLayout, resultLayout, txtResult)
        }

        // RESET: Back to Home
        btnReset.setOnClickListener {
            resultLayout.visibility = View.GONE
            homeLayout.visibility = View.VISIBLE
        }
    }

    // Helper function to swap screens and display advice
    private fun showAdvice(message: String, qLayout: LinearLayout, rLayout: LinearLayout, rText: TextView) {
        qLayout.visibility = View.GONE
        rLayout.visibility = View.VISIBLE
        rText.text = message
    }
}