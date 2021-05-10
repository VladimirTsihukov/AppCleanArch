package com.androidapp.historyscreen

import com.androidapp.historyscreen.history.FragmentHistory
import com.androidapp.historyscreen.history.ViewModelFragmentHistory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependenciesHistory() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    scope(named<FragmentHistory>()) {
        viewModel { ViewModelFragmentHistory(get()) }
    }
}