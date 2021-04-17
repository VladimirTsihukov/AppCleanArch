package com.androidapp.appcleanarch.view.interactor

import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.presenter.Interactor
import io.reactivex.Single

class InteractorMain(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getDataInteract(word: String, fromRemoteSource: Boolean): Single<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getDataRepository(word).map { AppState.Success(it) }
        } else {
            localRepository.getDataRepository(word).map { AppState.Success(it) }
        }
    }
}