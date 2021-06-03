package com.rsschool.android2021

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = context as Communicator
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        val min = view.findViewById<EditText>(R.id.min_value).text
        // TODO: val max = ...
        val max = view.findViewById<EditText>(R.id.max_value).text

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            if(min.isNotBlank() && max.isNotBlank()) {
                if (!isValidData(min, max)) {
                    Toast.makeText(context, "Invalid data", Toast.LENGTH_SHORT).show()
                } else if (min.toString().toInt() > max.toString().toInt()) {
                    Toast.makeText(context, "Min can't be less Max", Toast.LENGTH_SHORT).show()
                } else {
                    communicator.sendDataFromFirstToSecondFragment(min.toString().toInt(), max.toString().toInt())
                }

            } else {
                Toast.makeText(context, "Value cannot be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidData(min: Editable, max: Editable): Boolean {
        val isValidMinValue: Int? = try { min.toString().toInt() } catch (e: NumberFormatException) { null }
        val isValidMaxValue: Int? = try { max.toString().toInt() } catch (e: NumberFormatException) { null }

        return isValidMinValue != null && isValidMaxValue != null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}