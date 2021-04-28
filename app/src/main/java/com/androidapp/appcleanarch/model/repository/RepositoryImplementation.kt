package com.androidapp.appcleanarch.model.repository

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSource

class RepositoryImplementation (
    private val dataSource: DataSource<List<DataModel>>
        ) : Repository<List<DataModel>> {

    override suspend fun getDataRepository(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}