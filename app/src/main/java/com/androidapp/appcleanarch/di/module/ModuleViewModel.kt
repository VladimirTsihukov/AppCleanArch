package com.androidapp.appcleanarch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentMain
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
    @ViewModelKey(ViewModelFragmentMain::class)
    protected abstract fun mainViewModel(fragmentMain: ViewModelFragmentMain): ViewModel
}