package com.androidapp.core.interactor

interface Interactor<T> {
    suspend fun getDataInteract(word: String, fromRemoteSource: Boolean) : T
}