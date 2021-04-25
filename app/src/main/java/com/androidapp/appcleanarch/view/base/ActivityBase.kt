package com.androidapp.appcleanarch.view.base

import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.utils.network.isOnline
import com.androidapp.appcleanarch.view.main.fragment.fragmentDialog.FragmentAlertDialogNetWorkStatus

abstract class ActivityBas<T : AppState> : AppCompatActivity() {

    abstract val viewModel: ViewModelBase<T>

    private var isNetWorkStatus: Boolean = false

    abstract fun renderData(appState: AppState)

    override fun onResume() {
        super.onResume()
        isNetWorkStatus = isOnline(this)
        if (!isNetWorkStatus && isDialogNull()) {
            showAlertDialog()
        }
    }

    protected fun showAlertDialog() {
        FragmentAlertDialogNetWorkStatus.newInstance().show(supportFragmentManager, FragmentAlertDialogNetWorkStatus.TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(FragmentAlertDialogNetWorkStatus.TAG) == null
    }
}