package com.androidapp.appcleanarch.di.module

import com.androidapp.appcleanarch.di.NAME_LOCAL
import com.androidapp.appcleanarch.di.NAME_REMOTE
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource
import com.androidapp.appcleanarch.model.datasource.retrofit.RetrofitImplementation
import com.androidapp.appcleanarch.model.datasource.room.RoomDataBaseImplementation
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.model.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleRepository {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRemoteRepository(
        @Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>
    ) : Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideLocalRepository(
        @Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun providerDataSourceRemote() : DataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun providerDataSourceLocal() : DataSource<List<DataModel>> = RoomDataBaseImplementation()
}