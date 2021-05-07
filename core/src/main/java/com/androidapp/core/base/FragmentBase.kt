package com.androidapp.core.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidapp.core.R
import com.androidapp.model.data.AppState
import com.androidapp.utils.network.OnlineLiveData
import com.androidapp.utils.ui.FragmentAlertDialogNetWorkStatus

abstract class FragmentBase<T : AppState>(contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract val viewModel: ViewModelBase<T>

    private var isNetWorkState: Boolean = false

    abstract fun renderData(appState: AppState)

    override fun onResume() {
        super.onResume()
        subscribeToNetWorkChange()
    }

    private fun subscribeToNetWorkChange() {
        activity?.let { contextActivity ->
            OnlineLiveData(contextActivity).observe(viewLifecycleOwner, {
                isNetWorkState = it
                if (!isNetWorkState) {
                    showAlertDialog()
                    Toast.makeText(contextActivity,
                        R.string.dialog_message_device_is_offline,
                        Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    protected fun showAlertDialog() {
        FragmentAlertDialogNetWorkStatus.newInstance()
            .show(childFragmentManager, FragmentAlertDialogNetWorkStatus.TAG)
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(FragmentAlertDialogNetWorkStatus.TAG) == null
    }
}