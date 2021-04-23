package com.androidapp.appcleanarch.view.viewModel

import androidx.lifecycle.LiveData
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.view.base.ViewModelBase
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import javax.inject.Inject

class ViewModelMain @Inject constructor(
    private val interactor: InteractorMain
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