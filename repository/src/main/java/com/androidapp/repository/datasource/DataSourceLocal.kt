package com.androidapp.repository.datasource

import com.androidapp.model.data.DataModel


interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(word: String, dataModel: List<DataModel>)
}