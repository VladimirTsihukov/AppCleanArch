package com.androidapp.appcleanarch.view.base

import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.presenter.Presenter

abstract class ActivityBase<T : AppState> : AppCompatActivity(), IView {

    protected val presenter: Presenter by lazy {
        createPresenter()
    }

    protected abstract fun createPresenter() : Presenter

    abstract override fun renderData(appState: AppState)

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}