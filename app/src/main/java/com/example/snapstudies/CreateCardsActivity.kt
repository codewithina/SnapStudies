package com.example.snapstudies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CreateCardsActivity : AppCompatActivity() {

    private lateinit var glossaryList: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cards)

        val buttonAddWord = findViewById<Button>(R.id.buttonAddWord)
        val buttonDone = findViewById<Button>(R.id.buttonDone)
        val swedishKey = findViewById<EditText>(R.id.editKeySwedishWord)
        val englishValue = findViewById<EditText>(R.id.editValueEnglishWord)
        val textViewWordNo = findViewById<TextView>(R.id.textViewWordNo)
        val sharedPreferencesManager = SharedPreferenceManager(this)
        var wordCounter = 1

        glossaryList = HashMap()
        updateWordNumber(textViewWordNo, wordCounter)

        buttonAddWord.setOnClickListener {
            val key = swedishKey.text.toString()
            val value = englishValue.text.toString()
            if (key.isNotBlank() && value.isNotBlank()) {
                glossaryList.put(key, value)
                swedishKey.setText("")
                swedishKey.requestFocus()
                englishValue.setText("")
                wordCounter++
                updateWordNumber(textViewWordNo, wordCounter)
                Toast.makeText(this, "Ordet är registrerat.", Toast.LENGTH_SHORT).show()
                Log.d("!!!", glossaryList.toString())
            } else {
                Toast.makeText(this, "Fyll i orden korrekt.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDone.setOnClickListener {
            if (glossaryList.size > 7) {
                val userData = UserData(glossaryList, 0)
                sharedPreferencesManager.saveData("user_data", userData)
                //Log.d("!!!", glossaryList.toString())
                val intent = Intent(this, PracticeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Lägg till fler ord.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateWordNumber(textViewWordNo: TextView, wordCounter: Int) {
        val wordNumber = getString(R.string.textview_word_no, wordCounter)
        textViewWordNo.text = wordNumber
    }
}