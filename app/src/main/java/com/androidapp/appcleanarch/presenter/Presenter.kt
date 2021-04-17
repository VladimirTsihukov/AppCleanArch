package com.androidapp.appcleanarch.presenter

import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.view.base.IView

interface Presenter<T : AppState, V : IView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}