package com.example.snapstudies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CreateCardsActivity : AppCompatActivity() {

    private lateinit var glossaryList: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cards)

        glossaryList = HashMap()
        val buttonAddWord = findViewById<Button>(R.id.buttonAddWord)
        val buttonDone = findViewById<Button>(R.id.buttonDone)
        val swedishKey = findViewById<EditText>(R.id.editKeySwedishWord)
        val englishValue = findViewById<EditText>(R.id.editValueEnglishWord)

        buttonAddWord.setOnClickListener {
            val key = swedishKey.text.toString()
            val value = englishValue.text.toString()
            glossaryList.put(key, value)
            swedishKey.setText("")
            englishValue.setText("")
            Log.d("!!!", glossaryList.toString())
        }

        buttonDone.setOnClickListener {
            val intent = Intent(this, PracticeActivity::class.java)
            startActivity(intent)
        }


    }
}