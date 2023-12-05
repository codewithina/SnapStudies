package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class StaticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statics)

        val textViewTotalRounds = findViewById<TextView>(R.id.main_stats)

        val sharedPreferencesManager = SharedPreferenceManager(this)
        val savedUserData = sharedPreferencesManager.getData("user_data", UserData::class.java)

        if (savedUserData != null) {
            val totalRounds = savedUserData.totalRounds
            val sizeGlossaryList = savedUserData.glossaryList.size
            val correctAnswers = savedUserData.correctAnswers
            val mainText = getString(R.string.statistics_text, totalRounds, correctAnswers, sizeGlossaryList)
            textViewTotalRounds.text = mainText
        }
    }
}