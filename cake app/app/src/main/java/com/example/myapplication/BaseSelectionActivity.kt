package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BaseSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_selection)

        // 1. Find the name display TextView
        val txtUserName = findViewById<TextView>(R.id.txtUserNameDisplay)

        // 2. Fetch the name saved during login
        val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("USER_NAME", "Baker")

        // 3. Set the text to show the logged-in name
        txtUserName.text = savedName

        // 4. Find the cake cards
        val cardChoc = findViewById<CardView>(R.id.cardChocolate)
        val cardVanilla = findViewById<CardView>(R.id.cardVanilla)
        val cardStraw = findViewById<CardView>(R.id.cardStrawberry)

        // 5. ANIMATION LOGIC
        val fastPop = AnimationUtils.loadAnimation(this, R.anim.fast_pop)
        val floatAnim = AnimationUtils.loadAnimation(this, R.anim.floating)

        // Stagger the entry (Waterfall effect)
        cardChoc.startAnimation(fastPop)

        cardVanilla.postDelayed({
            cardVanilla.startAnimation(fastPop)
        }, 150)

        cardStraw.postDelayed({
            cardStraw.startAnimation(fastPop)
        }, 300)

        // Start floating loop after entry animations finish
        cardChoc.postDelayed({
            cardChoc.startAnimation(floatAnim)
            cardVanilla.startAnimation(floatAnim)
            cardStraw.startAnimation(floatAnim)
        }, 1000)

        // 6. Set up the cake card clicks
        cardChoc.setOnClickListener { saveAndNext("Rich Chocolate") }
        cardVanilla.setOnClickListener { saveAndNext("Classic Vanilla") }
        cardStraw.setOnClickListener { saveAndNext("Sweet Strawberry") }
    }

    private fun saveAndNext(baseName: String) {
        val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
        sharedPref.edit().putString("SELECTED_BASE", baseName).apply()

        Toast.makeText(this, "$baseName Selected!", Toast.LENGTH_SHORT).show()

        // Go to the next step (Toppings)
        val intent = Intent(this, ToppingsActivity::class.java)
        startActivity(intent)
    }
}