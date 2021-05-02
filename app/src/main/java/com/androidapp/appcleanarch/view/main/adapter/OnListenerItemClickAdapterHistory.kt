package com.androidapp.appcleanarch.view.main.adapter

import com.androidapp.repository.datasource.room.HistoryDataWord

interface OnListenerItemClickAdapterHistory {
    fun onItemClick(dataModel: HistoryDataWord)
    fun deleteWord(word: String)
}