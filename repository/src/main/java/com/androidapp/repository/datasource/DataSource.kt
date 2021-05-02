package com.androidapp.repository.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}