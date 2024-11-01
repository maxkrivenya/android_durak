package com.example.pms.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.pms.R

class CustomDialogBox : DialogFragment() {
    var yesButton: Button? = null
    var noButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.custom, container)
        yesButton = view.findViewById<View>(R.id.yes) as Button
        noButton = view.findViewById<View>(R.id.no) as Button

        yesButton!!.setOnClickListener(buttonListener)
        noButton!!.setOnClickListener(buttonListener)

        return view
    }

    private val buttonListener =
        View.OnClickListener { v ->
            if (v.id == R.id.yes) {
                (getActivity() as MainActivity).customDialogYes()
            }
            dismiss()
        }
}