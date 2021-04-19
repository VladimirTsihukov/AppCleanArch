package com.androidapp.appcleanarch.view.viewModel

import androidx.lifecycle.LiveData
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.datasource.retrofit.DataSourceRemote
import com.androidapp.appcleanarch.model.datasource.room.DataSourceLocal
import com.androidapp.appcleanarch.model.repository.RepositoryImplementation
import com.androidapp.appcleanarch.presenter.Interactor
import com.androidapp.appcleanarch.view.base.ViewModelBase
import com.androidapp.appcleanarch.view.interactor.InteractorMain

class ViewModelMain(
    private val interactor: Interactor<AppState> = InteractorMain(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    )
) : ViewModelBase<AppState>() {

    override fun getData(word: String, isOnline: Boolean) {
       compositeDisposable.add(
           interactor.getDataInteract(word, true)
               .subscribeOn(schedulerProvider.io())
               .observeOn(schedulerProvider.ui())
               .subscribe({
                   liveDataForView.value = it
               }, {
                   liveDataForView.value = AppState.Error(it)
               })
       )
    }

    fun subscriberLiveData() : LiveData<AppState> = liveDataForView
}