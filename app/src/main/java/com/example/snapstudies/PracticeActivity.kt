package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        //TODO: Switch to SharedPreferences later!!
        val temporaryGlossary = hashMapOf(
            "Apple" to "Äpple",
            "Banana" to "Banan",
            "Cherry" to "Körsbär",
            "Pear" to "Päron",
            "Elderberry" to "Fläderbär",
            "Fig" to "Fikon",
            "Grape" to "Druva",
            "Honeydew" to "Honungsmelon",
            "Orange" to "Apelsin",
            "Pineapple" to "Ananas"
        )

        val buttonOne: Button = findViewById(R.id.buttonOne)
        val buttonTwo: Button = findViewById(R.id.buttonTwo)
        val buttonThree: Button = findViewById(R.id.buttonThree)
        val buttonFour: Button = findViewById(R.id.buttonFour)
        val buttonFive: Button = findViewById(R.id.buttonFive)
        val buttonSix: Button = findViewById(R.id.buttonSix)

        val textViewWordOnCard = findViewById<TextView>(R.id.textViewWordOnCard)

        //Show random word from glossary on card
        val randomKey = temporaryGlossary.keys.random()
        textViewWordOnCard.text = randomKey

        //Set the meaning of word on card as text on random button
        val listOfButtons =
            mutableListOf(buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix)
        val randomRightAnswerButton = listOfButtons.random()
        randomRightAnswerButton.text = temporaryGlossary[randomKey]

        //Remove right answer from glossary list
        val wrongAnswerGlossaryList = temporaryGlossary.values.toMutableList()
        wrongAnswerGlossaryList.remove(temporaryGlossary[randomKey])

        //Shuffle the list
        val shuffledWrongAnswerGlossaryList = wrongAnswerGlossaryList.shuffled()

        //Remove right answer button from button list
        listOfButtons.remove(randomRightAnswerButton)

        //For every piece in wrongAnswerButtonList set the text
        for (i in listOfButtons.indices){
            listOfButtons[i].text = shuffledWrongAnswerGlossaryList[i]
        }




        buttonOne.setOnClickListener {
            //if buttonOne.text == textViewWordOnCard.text right fragment
            //else
        }
        buttonTwo.setOnClickListener { }
        buttonThree.setOnClickListener { }
        buttonFour.setOnClickListener { }
        buttonFive.setOnClickListener { }
        buttonSix.setOnClickListener { }

    }

    fun assignRandomAnswersToButtons(){
        //TODO
    }
}