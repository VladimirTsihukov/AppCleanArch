package com.androidapp.appcleanarch.model.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryDataWord::class], version = 1, exportSchema = false)
abstract class HistoryDbWord : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}