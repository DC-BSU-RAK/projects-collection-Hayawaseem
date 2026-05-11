package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // 1. Initialize UI components from activity_payment.xml
        val btnBack = findViewById<TextView>(R.id.btnBackPayment)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmPayment)
        val radioCash = findViewById<RadioButton>(R.id.radioCash)
        val radioCard = findViewById<RadioButton>(R.id.radioCardOnDelivery)

        // 2. Back Button Logic
        btnBack.setOnClickListener {
            finish()
        }

        // 3. Manual Toggle Logic (Fixes the CardView/RadioGroup issue)
        radioCash.setOnClickListener {
            radioCash.isChecked = true
            radioCard.isChecked = false
        }

        radioCard.setOnClickListener {
            radioCard.isChecked = true
            radioCash.isChecked = false
        }

        // 4. Trigger the Popup when Confirm Order is clicked
        btnConfirm.setOnClickListener {
            if (!radioCash.isChecked && !radioCard.isChecked) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            } else {
                showSuccessDialog()
            }
        }
    }

    // 5. Function to show the "Order Successfully" Dialog
    private fun showSuccessDialog() {
        // Create the dialog builder and inflate your custom XML
        val builder = android.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_success, null)
        builder.setView(view)
        builder.setCancelable(false) // Forces user to click DONE

        val dialog = builder.create()

        // Transparent background so the rounded corners of your layout show up properly
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Find the DONE button inside dialog_success.xml
        val btnDone = view.findViewById<Button>(R.id.btnDone)
        btnDone.setOnClickListener {
            dialog.dismiss()

            // Return to MainActivity and clear the activity stack
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        dialog.show()
    }
}