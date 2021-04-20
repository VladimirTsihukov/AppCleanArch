package com.androidapp.appcleanarch.view.base

import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.model.data.AppState

abstract class ActivityBas<T : AppState> : AppCompatActivity() {

    abstract val viewModel: ViewModelBase<T>

    abstract fun renderData(appState: AppState)
}