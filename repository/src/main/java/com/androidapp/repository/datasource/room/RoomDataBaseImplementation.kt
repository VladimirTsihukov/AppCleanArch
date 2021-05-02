package com.androidapp.repository.datasource.room

import com.androidapp.model.data.DataModel
import com.androidapp.repository.convertFindWordToEntity
import com.androidapp.repository.convertListEntityToDataModel
import com.androidapp.repository.datasource.DataSourceLocal

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun saveToDB(word: String, dataModel: List<DataModel>) {
        convertFindWordToEntity(word, dataModel)?.let {
            historyDao.addHistoryEntity(it)
        }
    }

    override suspend fun getData(word: String): List<DataModel> {
        val resultDB = historyDao.getListHistoryEntity()
        resultDB?.let {
            return convertListEntityToDataModel(resultDB)
        }
        return listOf()
    }
}