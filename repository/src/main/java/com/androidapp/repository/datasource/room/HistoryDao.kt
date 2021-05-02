package com.androidapp.repository.datasource.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM tableWord")
    suspend fun getListHistoryEntity(): List<HistoryDataWord>?

    @Query("SELECT word FROM tableWord")
    suspend fun getListWord(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListHistoryEntity(dataList: List<HistoryDataWord>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistoryEntity(data: HistoryDataWord)

    @Query("DELETE FROM tableWord")
    suspend fun deleteAllTable()

    @Delete
    suspend fun deleteHistoryEntity(data: HistoryDataWord)

    @Query ("DELETE FROM tableWord WHERE word = :word")
    suspend fun deleteWordForName(word: String)
}