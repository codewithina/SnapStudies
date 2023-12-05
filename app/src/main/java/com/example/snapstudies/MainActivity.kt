package com.example.snapstudies

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var practiceLanguage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddWords: Button = findViewById(R.id.buttonAddWords)
        val buttonStatistics: Button = findViewById(R.id.buttonStatistics)
        val buttonStartPractice: Button = findViewById(R.id.buttonStartPractice)
        val sharedPreferencesManager = SharedPreferenceManager(this)
        val userData: UserData? =
            sharedPreferencesManager.getData("user_data", UserData::class.java)

        buttonAddWords.setOnClickListener {
            val intent = Intent(this, CreateCardsActivity::class.java)
            startActivity(intent)
        }

        buttonStatistics.setOnClickListener {
            val intent = Intent(this, StaticsActivity::class.java)
            startActivity(intent)
        }

        buttonStartPractice.setOnClickListener {
            if (userData?.glossaryList?.isNotEmpty() == true) {
                val intent = Intent(this, PracticeActivity::class.java)
                chooseLanguage()
            } else {
                Toast.makeText(this, "Skapa gloslista först.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun chooseLanguage() {
        val builder = AlertDialog.Builder(this)

        // Set the message and title for the dialog
        builder.setTitle("Välj språk")
        builder.setMessage("Vilket språk vill du öva på?")

        // Add one button for Swedish and one for English
        builder.setPositiveButton("Svenska") { _, _ ->
            practiceLanguage = "Swedish"
            startPractice()
        }
        builder.setNegativeButton("Engelska") { _, _ ->
            practiceLanguage = "English"
            startPractice()
        }

        val dialog = builder.create()
        dialog.show()

        // Layout
        val drawable = GradientDrawable()
        drawable.cornerRadius = 20f
        drawable.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(drawable)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    private fun startPractice() {
        val intent = Intent(this, PracticeActivity::class.java)
        intent.putExtra("language", practiceLanguage)
        startActivity(intent)
    }

}