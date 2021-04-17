package com.androidapp.appcleanarch.presenter

import com.androidapp.appcleanarch.view.base.IView

interface Presenter {
    fun attachView(view: IView)
    fun detachView(view: IView)
    fun getData(word: String, isOnline: Boolean)
}