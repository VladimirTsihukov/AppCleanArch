package com.androidapp.appcleanarch.view.interactor

import com.androidapp.appcleanarch.di.NAME_LOCAL
import com.androidapp.appcleanarch.di.NAME_REMOTE
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.presenter.Interactor
import javax.inject.Inject
import javax.inject.Named

class InteractorMain @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getDataInteract(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource){
                remoteRepository
            } else {
                localRepository
            }.getDataRepository(word)
        )
    }
}