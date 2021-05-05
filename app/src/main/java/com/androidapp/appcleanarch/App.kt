package com.androidapp.appcleanarch

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        //просто запускаем Koin
        startKoin { androidContext(this@App)}
        instance = this
    }
}