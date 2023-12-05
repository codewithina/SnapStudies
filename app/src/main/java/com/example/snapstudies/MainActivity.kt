package com.example.snapstudies

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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
                startActivity(intent)
            } else {
                Toast.makeText(this, "Skapa gloslista först.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}