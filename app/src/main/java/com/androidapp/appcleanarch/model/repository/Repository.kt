package com.androidapp.appcleanarch.model.repository

import io.reactivex.Single

interface Repository<T> {
    fun getDataRepository(word : String) : Single<T>
}