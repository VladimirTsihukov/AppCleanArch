package com.androidapp.appcleanarch.view.interactor

import com.androidapp.core.interactor.Interactor
import com.androidapp.model.data.AppState
import com.androidapp.model.data.DataModel
import com.androidapp.repository.repository.Repository
import com.androidapp.repository.repository.RepositoryLocal

class InteractorMain(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getDataInteract(word: String, fromRemoteSource: Boolean): AppState {
        return if (fromRemoteSource) {
            val resultInServer = remoteRepository.getDataRepository(word)
            localRepository.saveToDB(word,  resultInServer)
            AppState.Success(resultInServer)
        } else {
            AppState.Success(localRepository.getDataRepository(word))
        }
    }
}