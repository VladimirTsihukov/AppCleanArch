package com.androidapp.appcleanarch.view.interactor

import com.androidapp.appcleanarch.di.NAME_LOCAL
import com.androidapp.appcleanarch.di.NAME_REMOTE
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.presenter.Interactor
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class InteractorMain @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getDataInteract(word: String, fromRemoteSource: Boolean): Single<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getDataRepository(word).map { AppState.Success(it) }
        } else {
            localRepository.getDataRepository(word).map { AppState.Success(it) }
        }
    }
}