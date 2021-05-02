package com.androidapp.repository.repository

interface Repository <T> {
    suspend fun getDataRepository(word : String): T
}