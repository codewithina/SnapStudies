package com.example.snapstudies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

private const val ARG_CORRECT_ANSWERS = "ARG_CORRECT_ANSWERS"

class EndFragment : Fragment() {

    private var correctAnswers: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            correctAnswers = it.getInt(ARG_CORRECT_ANSWERS, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end, container, false)

        val endMsg = view.findViewById<TextView>(R.id.endMsg)

        correctAnswers?.let {
            val endPracticeText = getString(R.string.end_practice, it)
            endMsg.text = endPracticeText
        }

        val relativeLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout)
        relativeLayout.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(correctAnswers: Int) =
            EndFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CORRECT_ANSWERS, correctAnswers)
                }
            }
    }
}