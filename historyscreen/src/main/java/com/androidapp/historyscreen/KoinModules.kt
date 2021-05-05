package com.androidapp.historyscreen

import com.androidapp.historyscreen.history.ViewModelFragmentHistory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependenciesHistory() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { ViewModelFragmentHistory(get()) }
}