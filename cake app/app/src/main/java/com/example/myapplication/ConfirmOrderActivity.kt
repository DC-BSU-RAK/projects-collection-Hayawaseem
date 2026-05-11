package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        // 1. Initialize all views
        val btnBack = findViewById<TextView>(R.id.btnBackConfirm)
        val imgOrderLarge = findViewById<ImageView>(R.id.imgOrderLarge)
        val txtOrderName = findViewById<TextView>(R.id.txtOrderName)
        val txtOrderPrice = findViewById<TextView>(R.id.txtOrderPrice)
        val txtSubtotal = findViewById<TextView>(R.id.txtSubtotal)
        val txtTotal = findViewById<TextView>(R.id.txtTotal)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        // 2. Set the button text to show we are moving to payment
        btnCheckout.text = "PROCEED TO PAYMENT"

        // 3. Back button logic
        btnBack.setOnClickListener { finish() }

        // 4. Get data from the previous screen
        val cakeName = intent.getStringExtra("CAKE_TITLE") ?: "Berry Choc Delight"
        val cakePrice = intent.getStringExtra("CAKE_PRICE") ?: "$45.00"

        txtOrderName.text = cakeName
        txtOrderPrice.text = cakePrice
        txtSubtotal.text = cakePrice

        // 5. Image logic based on the cake title
        when {
            cakeName.contains("Berry") -> imgOrderLarge.setImageResource(R.drawable.strawberries_chocolate)
            cakeName.contains("Double Chocolate") -> imgOrderLarge.setImageResource(R.drawable.chocolate_bar)
            cakeName.contains("Vanilla Party") -> imgOrderLarge.setImageResource(R.drawable.vanilla_sprinkles)
            cakeName.contains("Vanilla") -> imgOrderLarge.setImageResource(R.drawable.vanilla_chocolate)
            cakeName.contains("Strawberry Sprinkle") -> imgOrderLarge.setImageResource(R.drawable.strawberries_sprinkles)
            cakeName.contains("Chocolate Sprinkle") -> imgOrderLarge.setImageResource(R.drawable.chocolate_sprinkles)
            else -> imgOrderLarge.setImageResource(R.drawable.strawberries_chocolate)
        }

        // 6. Calculate total (Price + $5.00 Delivery)
        try {
            val numericPrice = cakePrice.replace("$", "").replace(" ", "").toDouble()
            val totalSum = numericPrice + 5.00
            txtTotal.text = String.format("$%.2f", totalSum)
        } catch (e: Exception) {
            txtTotal.text = cakePrice
        }

        // 7. THE FIX: Click listener specifically for PaymentActivity
        // Make sure there are NO other btnCheckout listeners in this file!
        btnCheckout.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}