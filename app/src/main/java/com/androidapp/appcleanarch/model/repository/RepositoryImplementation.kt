package com.androidapp.appcleanarch.model.repository

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource
import io.reactivex.Single

class RepositoryImplementation (
    private val dataSource: DataSource<List<DataModel>>
        ) : Repository<List<DataModel>> {

    override fun getDataRepository(word: String): Single<List<DataModel>> {
        return dataSource.getData(word)
    }
}