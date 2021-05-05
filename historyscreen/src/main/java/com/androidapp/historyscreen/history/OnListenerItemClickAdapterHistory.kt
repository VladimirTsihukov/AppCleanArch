package com.androidapp.historyscreen.history

import com.androidapp.repository.datasource.room.HistoryDataWord

interface OnListenerItemClickAdapterHistory {
    fun onItemClick(dataModel: HistoryDataWord)
    fun deleteWord(word: String)
}