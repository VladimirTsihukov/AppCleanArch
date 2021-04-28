package com.androidapp.appcleanarch

import android.app.Application
import com.androidapp.appcleanarch.diKoin.application
import com.androidapp.appcleanarch.diKoin.mainScreen
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
           modules(listOf(application, mainScreen))
        }
    }
}