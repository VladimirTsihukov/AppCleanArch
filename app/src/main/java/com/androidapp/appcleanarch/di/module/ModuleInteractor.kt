package com.androidapp.appcleanarch.di.module

import com.androidapp.appcleanarch.di.NAME_LOCAL
import com.androidapp.appcleanarch.di.NAME_REMOTE
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ModuleInteractor {

    @Provides
    internal fun provideInteractor (
        @Named(NAME_REMOTE) remoteRepository: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) localRepository: Repository<List<DataModel>>
    ) = InteractorMain(remoteRepository, localRepository)
}