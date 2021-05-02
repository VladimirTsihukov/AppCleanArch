package com.androidapp.appcleanarch.view.viewModel

import androidx.lifecycle.LiveData
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import com.androidapp.core.base.ViewModelBase
import com.androidapp.model.data.AppState
import kotlinx.coroutines.launch

class ViewModelFragmentMain(
    private val interactor: InteractorMain
) : ViewModelBase<AppState>() {

    fun subscriberLiveData(): LiveData<AppState> = liveDataForView

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForView.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutine.launch {
            val result = interactor.getDataInteract(word, isOnline)
            liveDataForView.postValue(result)
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