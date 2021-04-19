package com.androidapp.appcleanarch.presenter

import io.reactivex.Single

interface Interactor<T> {
    fun getDataInteract(word: String, fromRemoteSource: Boolean) : Single<T>
}