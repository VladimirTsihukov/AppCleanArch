package com.androidapp.appcleanarch.model.datasource.room

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource

class DataSourceLocal (
    private val remoteProvider : RoomDataBaseImplementation = RoomDataBaseImplementation()
        ) : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}