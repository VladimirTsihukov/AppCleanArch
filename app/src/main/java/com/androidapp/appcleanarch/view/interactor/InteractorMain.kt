package com.androidapp.appcleanarch.view.interactor

import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.presenter.Interactor

class InteractorMain (
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
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