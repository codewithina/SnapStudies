package com.example.snapstudies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateCardsActivity : AppCompatActivity() {

    lateinit var glossaryList: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cards)

        val sharedPreferencesManager = SharedPreferenceManager(this)
        glossaryList = HashMap()
        val buttonAddWord = findViewById<Button>(R.id.buttonAddWord)
        val buttonDone = findViewById<Button>(R.id.buttonDone)
        val swedishKey = findViewById<EditText>(R.id.editKeySwedishWord)
        val englishValue = findViewById<EditText>(R.id.editValueEnglishWord)

        buttonAddWord.setOnClickListener {
            val key = swedishKey.text.toString()
            val value = englishValue.text.toString()
            if (key.isNotBlank() && value.isNotBlank()) {
                glossaryList.put(key, value)
                swedishKey.setText("")
                englishValue.setText("")
                Log.d("!!!", glossaryList.toString())
            } else {
                Toast.makeText(this, "Fyll i orden korrekt.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDone.setOnClickListener {
            if (glossaryList.size > 7) {
                Log.d("!!!", "Done button clicked - before saving data")
                sharedPreferencesManager.saveData("user_glossary_list", glossaryList)
                Log.d("!!!", "Done button clicked - after saving data")
                val intent = Intent(this, PracticeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Lägg till fler ord.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}