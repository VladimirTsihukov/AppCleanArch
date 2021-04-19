package com.androidapp.appcleanarch.view.base

import com.androidapp.appcleanarch.model.data.AppState

interface IView {
    fun renderData(appState: AppState)
}