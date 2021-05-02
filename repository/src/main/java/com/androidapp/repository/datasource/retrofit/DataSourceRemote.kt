package com.androidapp.repository.datasource.retrofit

import com.androidapp.model.data.DataModel
import com.androidapp.repository.datasource.DataSource

class DataSourceRemote (
    private val remoteProvider : RetrofitImplementation = RetrofitImplementation()
        ) : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}