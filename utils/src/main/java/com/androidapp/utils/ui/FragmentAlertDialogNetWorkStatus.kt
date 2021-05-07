package com.androidapp.utils.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.androidapp.utils.getAlertDialog

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