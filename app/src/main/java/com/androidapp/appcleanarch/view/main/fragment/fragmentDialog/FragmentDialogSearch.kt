package com.androidapp.appcleanarch.view.main.fragment.fragmentDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.view.main.activity.ActivityMain
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_dialog_search.*

class FragmentDialogSearch : BottomSheetDialogFragment() {

    private var activityMain : ActivityMain? = null
    private lateinit var searchEditText: TextInputEditText
    private lateinit var searchButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchButton = btn_search_text
        searchEditText = text_search

        searchButton.setOnClickListener {
            if (searchEditText.text != null) {
                activityMain?.searchClick?.onClick(searchEditText.text.toString())
                dismiss()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activityMain = (activity as ActivityMain)
    }

    companion object {
        fun newInstance(): FragmentDialogSearch = FragmentDialogSearch()
        const val TAG = "FragmentDialogSearch"
    }
}