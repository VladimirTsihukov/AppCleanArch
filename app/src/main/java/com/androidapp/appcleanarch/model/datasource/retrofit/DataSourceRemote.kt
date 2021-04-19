package com.androidapp.appcleanarch.model.datasource.retrofit

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource
import io.reactivex.Single

class DataSourceRemote (
    private val remoteProvider : RetrofitImplementation = RetrofitImplementation()
        ) : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> = remoteProvider.getDataRetrofit(word)
}