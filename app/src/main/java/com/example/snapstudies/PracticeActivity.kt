package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class PracticeActivity : AppCompatActivity() {

    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var rightAnswerButton: Button
    private lateinit var listOfButtons: MutableList<Button>
    private lateinit var textViewWordOnCard: TextView
    private lateinit var newPracticeDeck: MutableList<String>
    private lateinit var userData: UserData

    private var cardCounter = 0
    private var correctAnswerCount = 0
    private var totalPracticeRounds = 0
    private var userGlossaryList: HashMap<String, String>? = null
    private lateinit var getGlossaryList: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        val sharedPreferencesManager = SharedPreferenceManager(this)
        userData = UserData(hashMapOf(),0, 0, )
        getGlossaryList = sharedPreferencesManager.getData("user_glossary_list", UserData::class.java)!!
        userGlossaryList = getGlossaryList.glossaryList



        textViewWordOnCard = findViewById(R.id.textViewWordOnCard)
        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)

        listOfButtons = mutableListOf(buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix)
        newPracticeDeck = userGlossaryList?.keys?.toMutableList()!!

        newCard()

        for (button in listOfButtons) {
            button.setOnClickListener {
                if (it == rightAnswerButton) {
                    correctAnswerCount++
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
        Log.d("!!!", correctAnswerCount.toString())

        if (newPracticeDeck.isEmpty()) {
            val newFragment = EndFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.rightOrWrongFragmentContainer, newFragment)
                .commit()
            totalPracticeRounds++
            return
        }

        rightAnswerButton = listOfButtons.random()

        //Show random word from glossary on card
        val randomKey = newPracticeDeck.random()
        newPracticeDeck.remove(randomKey)
        textViewWordOnCard.text = randomKey

        //Set the meaning of word on card as text on random button
        rightAnswerButton.text = userGlossaryList?.get(randomKey)

        //Remove right answer from glossary list
        val wrongAnswerGlossaryList = userGlossaryList?.values?.toMutableList()!!
        wrongAnswerGlossaryList.remove(userGlossaryList?.get(randomKey))
        wrongAnswerGlossaryList.shuffle()

        //Remove right answer button from button list
        listOfButtons.remove(rightAnswerButton)

        //For every piece in wrongAnswerButtonList shuffle & set the text
        for (i in listOfButtons.indices) {
            listOfButtons[i].text = wrongAnswerGlossaryList[i]
        }
        listOfButtons.add(rightAnswerButton)
        cardCounter++
    }

    fun assignRandomAnswersToButtons() {
        //TODO
    }
}