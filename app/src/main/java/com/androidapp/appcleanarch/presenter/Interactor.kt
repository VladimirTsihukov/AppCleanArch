package com.androidapp.appcleanarch.presenter

interface Interactor<T> {
    suspend fun getDataInteract(word: String, fromRemoteSource: Boolean) : T
}