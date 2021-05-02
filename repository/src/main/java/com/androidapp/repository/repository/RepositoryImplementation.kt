package com.androidapp.repository.repository

import com.androidapp.model.data.DataModel
import com.androidapp.repository.datasource.DataSource

class RepositoryImplementation (
    private val dataSource: DataSource<List<DataModel>>
        ) : Repository<List<DataModel>> {

    override suspend fun getDataRepository(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}