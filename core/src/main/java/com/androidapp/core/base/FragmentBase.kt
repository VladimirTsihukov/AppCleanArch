package com.androidapp.core.base

import androidx.fragment.app.Fragment
import com.androidapp.model.data.AppState
import com.androidapp.utils.FragmentAlertDialogNetWorkStatus
import com.androidapp.utils.network.isOnline

abstract class FragmentBase<T: AppState>(contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract val viewModel: ViewModelBase<T>

    private var isNetWorkState: Boolean = false

    abstract fun renderData(appState: AppState)

    override fun onResume() {
        super.onResume()
        activity?.let {
            isNetWorkState = isOnline(it)
            if (!isNetWorkState && isDialogNull()) {
                showAlertDialog()
            }
        }
    }

    protected fun showAlertDialog() {
        FragmentAlertDialogNetWorkStatus.newInstance().show(childFragmentManager, FragmentAlertDialogNetWorkStatus.TAG)
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(FragmentAlertDialogNetWorkStatus.TAG) == null
    }
}