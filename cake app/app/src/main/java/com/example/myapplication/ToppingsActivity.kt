package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class ToppingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toppings)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        btnBack?.setOnClickListener { finish() }

        val cardChoc = findViewById<CardView>(R.id.cardToppingChoc)
        val cardSprinkles = findViewById<CardView>(R.id.cardToppingSprinkles)

        // Animations for the topping cards
        val fastPop = AnimationUtils.loadAnimation(this, R.anim.fast_pop)
        val floatAnim = AnimationUtils.loadAnimation(this, R.anim.floating)

        cardChoc?.startAnimation(fastPop)
        cardSprinkles?.postDelayed({
            cardSprinkles.startAnimation(fastPop)
        }, 150)

        cardChoc?.postDelayed({
            cardChoc.startAnimation(floatAnim)
            cardSprinkles?.startAnimation(floatAnim)
        }, 1000)

        val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
        val selectedBase = sharedPref.getString("SELECTED_BASE", "Rich Chocolate") ?: "Rich Chocolate"

        cardChoc?.setOnClickListener {
            saveTopping("Chocolate Bar")
            showMasterpiece(selectedBase, "Chocolate Bar")
        }

        cardSprinkles?.setOnClickListener {
            saveTopping("Rainbow Sprinkles")
            showMasterpiece(selectedBase, "Rainbow Sprinkles")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTopping(toppingName: String) {
        val sharedPref = getSharedPreferences("BakeryData", Context.MODE_PRIVATE)
        sharedPref.edit().putString("SELECTED_TOPPING", toppingName).apply()
    }

    private fun showMasterpiece(base: String, topping: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_masterpiece)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val imgResult = dialog.findViewById<ImageView>(R.id.imgFinalProduct)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtFinalTitle)
        val txtPrice = dialog.findViewById<TextView>(R.id.txtFinalPrice)
        val btnClose = dialog.findViewById<Button>(R.id.btnDone)

        val btnSmall = dialog.findViewById<Button>(R.id.btnSmall)
        val btnMedium = dialog.findViewById<Button>(R.id.btnMedium)
        val btnLarge = dialog.findViewById<Button>(R.id.btnLarge)

        val sizeButtons = listOf(btnSmall, btnMedium, btnLarge)

        // Helper to update button colors and the price text
        fun updateSelection(selected: Button, price: String) {
            txtPrice?.text = price
            sizeButtons.forEach { btn ->
                if (btn == selected) {
                    btn?.setBackgroundColor(Color.BLACK)
                    btn?.setTextColor(Color.WHITE)
                } else {
                    btn?.setBackgroundColor(Color.parseColor("#E7D9CC"))
                    btn?.setTextColor(Color.BLACK)
                }
            }
        }

        // Set listeners for dynamic price updates based on your design
        btnSmall?.setOnClickListener { updateSelection(btnSmall, "30$") }
        btnMedium?.setOnClickListener { updateSelection(btnMedium, "50$") }
        btnLarge?.setOnClickListener { updateSelection(btnLarge, "75$") }

        // Logic to display the correct cake combination
        when {
            base.contains("Strawberry") && topping == "Chocolate Bar" -> {
                imgResult?.setImageResource(R.drawable.strawberries_chocolate)
                txtTitle?.text = "Berry Choc Delight!"
                txtPrice?.text = "45$"
            }
            base.contains("Strawberry") && topping == "Rainbow Sprinkles" -> {
                imgResult?.setImageResource(R.drawable.strawberries_sprinkles)
                txtTitle?.text = "Strawberry Sprinkle Dream!"
                txtPrice?.text = "40$"
            }
            base.contains("Chocolate") && topping == "Chocolate Bar" -> {
                imgResult?.setImageResource(R.drawable.chocolate_bar)
                txtTitle?.text = "Double Chocolate Masterpiece!"
                txtPrice?.text = "50$"
            }
            base.contains("Chocolate") && topping == "Rainbow Sprinkles" -> {
                imgResult?.setImageResource(R.drawable.chocolate_sprinkles)
                txtTitle?.text = "Chocolate Sprinkle Surprise!"
                txtPrice?.text = "45$"
            }
            base.contains("Vanilla") && topping == "Chocolate Bar" -> {
                imgResult?.setImageResource(R.drawable.vanilla_chocolate)
                txtTitle?.text = "Classic Vanilla & Choc!"
                txtPrice?.text = "35$"
            }
            base.contains("Vanilla") && topping == "Rainbow Sprinkles" -> {
                imgResult?.setImageResource(R.drawable.vanilla_sprinkles)
                txtTitle?.text = "Vanilla Party Cake!"
                txtPrice?.text = "30$"
            }
        }

        btnClose?.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, ConfirmOrderActivity::class.java)
            intent.putExtra("CAKE_TITLE", txtTitle?.text.toString())
            intent.putExtra("CAKE_PRICE", txtPrice?.text.toString())
            startActivity(intent)
        }

        dialog.show()
    }
}