package com.example.snapstudies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddWords: Button = findViewById(R.id.buttonAddWords)
        val buttonStatistics: Button = findViewById(R.id.buttonStatistics)
        val buttonStartPractice: Button = findViewById(R.id.buttonStartPractice)
        val sharedPreferencesManager = SharedPreferenceManager(this)
        val userGlossary: HashMap<String, String>? =
            sharedPreferencesManager.getData("user_glossary_list", HashMap::class.java)

        buttonAddWords.setOnClickListener {
            val intent = Intent(this, CreateCardsActivity::class.java)
            startActivity(intent)
        }

        buttonStatistics.setOnClickListener {
            val intent = Intent(this, StaticsActivity::class.java)
            startActivity(intent)
        }

        buttonStartPractice.setOnClickListener {

            val intent = Intent(this, PracticeActivity::class.java)
            startActivity(intent)

        }


    }
}