package com.androidapp.appcleanarch.view.main.adapter

import com.androidapp.repository.datasource.room.HistoryDataWord

interface OnListenerItemClickAdapterMain {
    fun onItemClick(dataModel: HistoryDataWord)
}