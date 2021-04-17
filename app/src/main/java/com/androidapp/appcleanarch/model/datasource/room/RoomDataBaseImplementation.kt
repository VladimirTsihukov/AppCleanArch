package com.androidapp.appcleanarch.model.datasource.room

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource
import io.reactivex.Single

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> {
        TODO("Not yet implemented")
    }
}