package com.androidapp.appcleanarch

import android.app.Application
import com.androidapp.appcleanarch.di.AppComponent
import com.androidapp.appcleanarch.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.builder()
            .appContext(this)
            .build()

        App.component = component
    }

    companion object {
        lateinit var component: AppComponent
    }
}