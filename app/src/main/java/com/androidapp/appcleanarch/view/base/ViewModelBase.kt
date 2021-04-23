package com.androidapp.appcleanarch.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class ViewModelBase<T : AppState>(
    protected val liveDataForView: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}