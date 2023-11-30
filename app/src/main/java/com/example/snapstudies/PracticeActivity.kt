package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class PracticeActivity : AppCompatActivity() {

    //TODO: Switch to SharedPreferences later!!
    private val temporaryGlossary = hashMapOf(
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


    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button

    private lateinit var listOfButtons: MutableList<Button>

    private lateinit var randomRightAnswerButton: Button
    private lateinit var textViewWordOnCard: TextView

    private var cardCounter = 0
    private val maxCards = temporaryGlossary.size

    //Copy of list to go through every word when practicing
    private lateinit var newPracticeDeck: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        textViewWordOnCard = findViewById(R.id.textViewWordOnCard)

        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)
        listOfButtons =
            mutableListOf(buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix)

        newPracticeDeck = temporaryGlossary.keys.toMutableList()

        newCard()

        //Tell the buttons what to do when clicking right or wrong answer
        for (button in listOfButtons) {
            button.setOnClickListener {
                if (it == randomRightAnswerButton) {
                    val transaction = supportFragmentManager.beginTransaction()
                    val rightAnswerFragment = RightAnswerFragment()
                    transaction.add(R.id.rightOrWrongFragmentContainer, rightAnswerFragment)
                    transaction.commit()
                } else {
                    val transaction = supportFragmentManager.beginTransaction()
                    val wrongAnswerFragment = WrongAnswerFragment()
                    transaction.add(R.id.rightOrWrongFragmentContainer, wrongAnswerFragment)
                    transaction.commit()
                }
            }
        }
    }

    fun newCard() {
        randomRightAnswerButton = listOfButtons.random()

        //Show random word from glossary on card
        val randomKey = newPracticeDeck.random()
        newPracticeDeck.remove(randomKey)
        textViewWordOnCard.text = randomKey

        //Set the meaning of word on card as text on random button
        randomRightAnswerButton.text = temporaryGlossary[randomKey]

        //Remove right answer from glossary list
        val wrongAnswerGlossaryList = temporaryGlossary.values.toMutableList()
        wrongAnswerGlossaryList.remove(temporaryGlossary[randomKey])

        //Shuffle the list
        wrongAnswerGlossaryList.shuffle()

        //Remove right answer button from button list
        listOfButtons.remove(randomRightAnswerButton)

        //For every piece in wrongAnswerButtonList shuffle & set the text
        for (i in listOfButtons.indices) {
            listOfButtons[i].text = wrongAnswerGlossaryList[i]
        }

        listOfButtons.add(randomRightAnswerButton)
        cardCounter++

        // Show fragment when all cards been showed
        if (cardCounter == maxCards) {
            Log.d("!!!", "Show end")
            showEndFragment()
        }
    }

    private fun showEndFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val endFragment = EndFragment()
        transaction.replace(R.id.rightOrWrongFragmentContainer, endFragment)
        transaction.commit()
    }

    fun assignRandomAnswersToButtons() {
        //TODO
    }
}