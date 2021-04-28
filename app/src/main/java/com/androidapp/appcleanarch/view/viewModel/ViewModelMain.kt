package com.androidapp.appcleanarch.view.viewModel

import androidx.lifecycle.LiveData
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.view.base.ViewModelBase
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import kotlinx.coroutines.launch

class ViewModelMain (
    private val interactor: InteractorMain
) : ViewModelBase<AppState>() {

    fun subscriberLiveData(): LiveData<AppState> = liveDataForView

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForView.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutine.launch {
            liveDataForView.postValue(interactor.getDataInteract(word, true))
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForView.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForView.value = AppState.Success(null)
        super.onCleared()
    }
}