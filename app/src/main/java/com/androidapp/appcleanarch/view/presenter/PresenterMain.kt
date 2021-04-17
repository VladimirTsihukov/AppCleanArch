package com.androidapp.appcleanarch.view.presenter

import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.datasource.retrofit.DataSourceRemote
import com.androidapp.appcleanarch.model.datasource.room.DataSourceLocal
import com.androidapp.appcleanarch.model.repository.RepositoryImplementation
import com.androidapp.appcleanarch.presenter.Presenter
import com.androidapp.appcleanarch.rx.SchedulerProvider
import com.androidapp.appcleanarch.view.base.IView
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import io.reactivex.disposables.CompositeDisposable

class PresenterMain<T : AppState, V : IView>(
    private val interaptor: InteractorMain = InteractorMain(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
       compositeDisposable.add(
           interaptor.getDataInteract(word, true)
               .subscribeOn(schedulerProvider.io())
               .observeOn(schedulerProvider.ui())
               .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
               .subscribe({
                   currentView?.renderData(it)
               }, {
                   currentView?.renderData(AppState.Error(it))
               })
       )
    }
}