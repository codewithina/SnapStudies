package com.example.snapstudies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

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
    private lateinit var textViewWordOnCard: TextView
    private lateinit var newPracticeDeck: MutableList<String>
    private lateinit var userData: UserData
    private var practiceLanguage: String? = null

    private var cardCounter = 0
    private var correctAnswerCount = 0
    private var userGlossaryList: HashMap<String, String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        sharedPreferencesManager = SharedPreferenceManager(this)
        userData = UserData(hashMapOf(), 0, 0)
        userData = sharedPreferencesManager.getData("user_data", UserData::class.java)!!
        userGlossaryList = userData.glossaryList

        textViewWordOnCard = findViewById(R.id.textViewWordOnCard)
        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)

        practiceLanguage = intent.getStringExtra("language")

        listOfButtons =
            mutableListOf(buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix)
        newPracticeDeck = userGlossaryList?.keys?.toMutableList()!!

        newNewCard()

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

    fun newNewCard() {

        if (newPracticeDeck.isEmpty()) {
            // Save updated values to user_data
            val savedUserData =
                sharedPreferencesManager.getData("user_data", UserData::class.java)!!
            userData.totalRounds = savedUserData.totalRounds + 1
            userData.correctAnswers = correctAnswerCount
            sharedPreferencesManager.saveData("user_data", userData)

            // Open end fragment and return to main
            val newFragment = EndFragment.newInstance(correctAnswerCount)
            supportFragmentManager.beginTransaction()
                .replace(R.id.rightOrWrongFragmentContainer, newFragment)
                .commit()
            return
        }

        rightAnswerButton = listOfButtons.random()

        // Show random word from glossary on card
        val randomKey = newPracticeDeck.random()
        newPracticeDeck.remove(randomKey)

        val wrongAnswerGlossaryList: MutableList<String>

        if (practiceLanguage == "Swedish") {
            textViewWordOnCard.text = randomKey
            rightAnswerButton.text = userGlossaryList?.get(randomKey)
            wrongAnswerGlossaryList = userGlossaryList?.values?.toMutableList()!!
            wrongAnswerGlossaryList.remove(userGlossaryList?.get(randomKey))
        } else { // If practiceLanguage is "English"
            val randomValue = userGlossaryList?.get(randomKey)
            textViewWordOnCard.text = randomValue
            rightAnswerButton.text = randomKey
            wrongAnswerGlossaryList = userGlossaryList?.keys?.toMutableList()!!
            wrongAnswerGlossaryList.remove(randomKey)
        }

        wrongAnswerGlossaryList.shuffle()

        listOfButtons.remove(rightAnswerButton)

        createWrongButtons(wrongAnswerGlossaryList)

        listOfButtons.add(rightAnswerButton)
        cardCounter++
    }

    fun createWrongButtons(wrongAnswerGlossaryList: MutableList<String>){
        // For every piece in wrongAnswerButtonList shuffle & set the text
        for (i in listOfButtons.indices) {
            listOfButtons[i].text = wrongAnswerGlossaryList[i]
        }
    }
}