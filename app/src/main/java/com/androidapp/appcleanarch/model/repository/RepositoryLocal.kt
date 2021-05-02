package com.androidapp.appcleanarch.model.repository

import com.androidapp.model.data.DataModel


interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(word:String, dataModel: List<DataModel>)
}