package com.androidapp.appcleanarch.di

import android.content.Context
import com.androidapp.appcleanarch.di.module.ModuleInteractor
import com.androidapp.appcleanarch.di.module.ModuleRepository
import com.androidapp.appcleanarch.di.module.ModuleViewModel
import com.androidapp.appcleanarch.view.main.activity.ActivityMain
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    ModuleInteractor::class,
    ModuleViewModel::class,
    ModuleRepository::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build() : AppComponent
    }

    fun inject(activity: ActivityMain)
}