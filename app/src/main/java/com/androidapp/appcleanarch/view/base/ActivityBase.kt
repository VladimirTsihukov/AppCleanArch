package com.androidapp.appcleanarch.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.presenter.Presenter

abstract class ActivityBase<T : AppState> : AppCompatActivity(), IView {

    protected lateinit var presenter: Presenter<T, IView>

    protected abstract fun createPresenter() : Presenter<T, IView>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}