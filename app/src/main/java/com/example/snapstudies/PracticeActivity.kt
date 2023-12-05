package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PracticeActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferenceManager
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var rightAnswerButton: Button
    private lateinit var listOfButtons: MutableList<Button>
    private lateinit var wordOnCard: TextView
    private lateinit var newPracticeDeck: MutableList<String>
    private lateinit var userData: UserData

    private var practiceLanguage: String? = null
    private var cardCounter = 0
    private var correctAnswerCount = 0
    private var userGlossaryList: HashMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        wordOnCard= findViewById(R.id.textViewWordOnCard)
        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)
        listOfButtons =
            mutableListOf(buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix)
        practiceLanguage = intent.getStringExtra("language")

        createNewDeck()
        openNewCard()

        setupButtonClickListeners()
    }

    fun openNewCard() {

        if (newPracticeDeck.isEmpty()) {
            updateUserData()
            endPractice()
            return
        }

        rightAnswerButton = listOfButtons.random()

        // Show random word from glossary on card
        val randomKey = newPracticeDeck.random()
        newPracticeDeck.remove(randomKey)

        // Action due to language choice before starting practice
        val wrongAnswerGlossaryList: MutableList<String>
        if (practiceLanguage == "Swedish") {
            wordOnCard.text = randomKey
            rightAnswerButton.text = userGlossaryList?.get(randomKey)
            wrongAnswerGlossaryList = userGlossaryList?.values?.toMutableList()!!
            wrongAnswerGlossaryList.remove(userGlossaryList?.get(randomKey))
        } else { // If practiceLanguage is "English"
            val randomValue = userGlossaryList?.get(randomKey)
            wordOnCard.text = randomValue
            rightAnswerButton.text = randomKey
            wrongAnswerGlossaryList = userGlossaryList?.keys?.toMutableList()!!
            wrongAnswerGlossaryList.remove(randomKey)
        }

        createWrongButtons(wrongAnswerGlossaryList)

        listOfButtons.add(rightAnswerButton)
        cardCounter++
        cardsOpenedText(cardCounter)
    }

    private fun createNewDeck(){
        sharedPreferencesManager = SharedPreferenceManager(this)
        userData = UserData(hashMapOf(), 0, 0)
        userData = sharedPreferencesManager.getData("user_data", UserData::class.java)!!
        userGlossaryList = userData.glossaryList
        newPracticeDeck = userGlossaryList?.keys?.toMutableList()!!
    }

    private fun openResultFragment(clickedButton : Button){
        if (clickedButton == rightAnswerButton) {
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

    private fun setupButtonClickListeners() {
        for (button in listOfButtons) {
            button.setOnClickListener {
                openResultFragment(button)
            }
        }
    }

    private fun createWrongButtons(wrongAnswerGlossaryList: MutableList<String>){
        // Shuffle glossary and remove right button alternative
        wrongAnswerGlossaryList.shuffle()
        listOfButtons.remove(rightAnswerButton)
        // For every piece in wrongAnswerButtonList set the text
        for (i in listOfButtons.indices) {
            listOfButtons[i].text = wrongAnswerGlossaryList[i]
        }
    }

    private fun endPractice(){
        val newFragment = EndFragment.newInstance(correctAnswerCount)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rightOrWrongFragmentContainer, newFragment)
            .commit()
    }

    private fun updateUserData(){
        val savedUserData =
            sharedPreferencesManager.getData("user_data", UserData::class.java)!!
        userData.totalRounds = savedUserData.totalRounds + 1
        userData.correctAnswers = correctAnswerCount
        sharedPreferencesManager.saveData("user_data", userData)
    }

    private fun cardsOpenedText(cardCounter : Int){
        val textViewCardsLeft = findViewById<TextView>(R.id.cardsLeft)

        val sharedPreferencesManager = SharedPreferenceManager(this)
        val savedUserData = sharedPreferencesManager.getData("user_data", UserData::class.java)

        if (savedUserData != null) {
            val sizeGlossaryList = savedUserData.glossaryList.size
            val cardsOpened =
                getString(R.string.opened_cards, cardCounter, sizeGlossaryList)
            textViewCardsLeft.text = cardsOpened
        }
    }
}