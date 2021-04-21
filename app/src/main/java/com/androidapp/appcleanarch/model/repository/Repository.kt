package com.androidapp.appcleanarch.model.repository

interface Repository <T> {
    suspend fun getDataRepository(word : String): T
}