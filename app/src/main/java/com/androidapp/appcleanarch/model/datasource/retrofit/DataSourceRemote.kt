package com.androidapp.appcleanarch.model.datasource.retrofit

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource

class DataSourceRemote (
    private val remoteProvider : RetrofitImplementation = RetrofitImplementation()
        ) : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}