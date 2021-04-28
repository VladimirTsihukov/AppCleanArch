package com.androidapp.appcleanarch.model.datasource.room

import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.DataSourceLocal
import com.androidapp.appcleanarch.utils.convertFindWordToEntity
import com.androidapp.appcleanarch.utils.convertListEntityToDataModel

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