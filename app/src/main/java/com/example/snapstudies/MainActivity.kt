package com.example.snapstudies

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBg = findViewById<View>(R.id.startFadeOut)
        val startLogo = findViewById<ImageView>(R.id.logoFadeOut)

        startFadeOut(startBg, startLogo)

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
            if (userGlossary?.isNotEmpty() == true) {
                val intent = Intent(this, PracticeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Skapa gloslista först.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startFadeOut (startBg : View, startLogo : ImageView){

        val fadeOutBg = ObjectAnimator.ofFloat(startBg, "alpha", 1.0f, 0.0f)
        fadeOutBg.duration = 3000
        val fadeOutLogo = ObjectAnimator.ofFloat(startLogo, "alpha", 1.0f, 0.0f)
        fadeOutLogo.duration = 3000

        fadeOutBg.start()
        fadeOutLogo.start()

    }
}