package com.androidapp.appcleanarch.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidapp.appcleanarch.model.data.AppState
import kotlinx.coroutines.*

abstract class ViewModelBase<T : AppState>(
    protected val liveDataForView: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    abstract fun getData(word: String, isOnline: Boolean)

    abstract fun handleError(error: Throwable)

    protected val viewModelCoroutine = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()   //независимое выполнения корутин, если какая-нибудь корутина
                                    // упадет с ошибкой остальные будут выполняться нормально

                + CoroutineExceptionHandler { _, throwable ->  //перехватываем ошибки
            handleError(throwable)
        }
    )

    fun cancelJob() {
        viewModelCoroutine.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}