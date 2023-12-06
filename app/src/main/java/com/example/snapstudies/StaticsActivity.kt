package com.example.snapstudies

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView

class StaticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statics)

        supportActionBar?.hide()

        val textViewStatistics = findViewById<TextView>(R.id.main_stats)
        val textViewStatistics2 = findViewById<TextView>(R.id.main_stats2)

        val sharedPreferencesManager = SharedPreferenceManager(this)
        val savedUserData = sharedPreferencesManager.getData("user_data", UserData::class.java)

        if (savedUserData != null) {
            val totalRounds = savedUserData.totalRounds
            val sizeGlossaryList = savedUserData.glossaryList.size
            val correctAnswers = savedUserData.correctAnswers

            val mainText = getString(R.string.statistics_text, totalRounds)
            val mainText2 = getString(R.string.statistics_text2, correctAnswers, sizeGlossaryList)
            val spannableString = SpannableString.valueOf(Html.fromHtml(mainText, Html.FROM_HTML_MODE_LEGACY))
            val spannableString2 = SpannableString.valueOf(Html.fromHtml(mainText2, Html.FROM_HTML_MODE_LEGACY))

            val startIndex1 = mainText.indexOf("$totalRounds")
            val endIndex1 = startIndex1 + "$totalRounds".length

            val startIndex2 = mainText2.indexOf("$correctAnswers")
            val endIndex2 = startIndex2 + "$correctAnswers".length

            val startIndex3 = mainText2.indexOf("$sizeGlossaryList", endIndex2)
            val endIndex3 = startIndex3 + "$sizeGlossaryList".length

            spannableString.setSpan(StyleSpan(Typeface.BOLD), startIndex1, endIndex1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(AbsoluteSizeSpan(90), startIndex1, endIndex1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString2.setSpan(StyleSpan(Typeface.BOLD), startIndex2, endIndex2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString2.setSpan(AbsoluteSizeSpan(90), startIndex2, endIndex2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString2.setSpan(StyleSpan(Typeface.BOLD), startIndex3, endIndex3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString2.setSpan(AbsoluteSizeSpan(90), startIndex3, endIndex3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            textViewStatistics.text = spannableString
            textViewStatistics2.text = spannableString2
        }
    }
}