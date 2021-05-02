package com.androidapp.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class FragmentAlertDialogNetWorkStatus : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        context?.let {
            return getAlertDialog(context)
        }
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val TAG = "FragmentAlertDialogNetWorkStatus"
        fun newInstance() = FragmentAlertDialogNetWorkStatus()
    }
}