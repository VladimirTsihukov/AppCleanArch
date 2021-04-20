package com.androidapp.appcleanarch

import android.app.Application
import com.androidapp.appcleanarch.di.AppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //val component = DaggerAppComponent.builder()

    }

    companion object {
        lateinit var component: AppComponent
    }
}