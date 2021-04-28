package com.androidapp.appcleanarch

import android.app.Application
import com.androidapp.appcleanarch.diKoin.application
import com.androidapp.appcleanarch.diKoin.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen))
        }
        instance = this
    }
}