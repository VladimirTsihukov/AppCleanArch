package com.androidapp.appcleanarch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidapp.appcleanarch.view.viewModel.ViewModelMain
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ModuleInteractor::class])
internal abstract class ModuleViewModel {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    protected abstract fun mainViewModel(main: ViewModelMain): ViewModel
}