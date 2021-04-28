package com.androidapp.appcleanarch.model.repository

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>)
    : RepositoryLocal<List<DataModel>> {

    override suspend fun getDataRepository(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(word: String, dataModel: List<DataModel>) {
        dataSource.saveToDB(word, dataModel)
    }
}