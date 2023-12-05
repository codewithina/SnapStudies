package com.example.snapstudies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

class WrongAnswerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wrong_answer, container, false)
        val relativeLayout = view.findViewById<ConstraintLayout>(R.id.relativeLayout)
        relativeLayout.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as PracticeActivity).newCard()
    }
}