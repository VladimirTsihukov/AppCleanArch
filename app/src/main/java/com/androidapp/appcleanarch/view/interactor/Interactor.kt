package com.androidapp.appcleanarch.view.interactor

interface Interactor<T> {
    suspend fun getDataInteract(word: String, fromRemoteSource: Boolean) : T
}