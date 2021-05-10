package com.androidapp.core.base

import androidx.fragment.app.Fragment
import com.androidapp.model.data.AppState
import com.androidapp.utils.network.OnlineLiveData
import com.androidapp.utils.ui.FragmentAlertDialogNetWorkStatus

abstract class FragmentBase<T : AppState>(contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract val viewModel: ViewModelBase<T>

    private var isNetWorkState: Boolean = false

    abstract fun renderData(appState: AppState)

    private fun subscribeToNetWorkChange() {
        activity?.let { contextActivity ->
            OnlineLiveData(contextActivity).observe(viewLifecycleOwner, {
                isNetWorkState = it
                if (!isNetWorkState) {
                    showAlertDialog()
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