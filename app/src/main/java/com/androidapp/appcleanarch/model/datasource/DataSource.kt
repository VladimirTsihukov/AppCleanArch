package com.androidapp.appcleanarch.model.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}