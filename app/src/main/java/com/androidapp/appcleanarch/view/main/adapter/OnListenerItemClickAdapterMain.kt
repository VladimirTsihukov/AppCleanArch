package com.androidapp.appcleanarch.view.main.adapter

import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord

interface OnListenerItemClickAdapterMain {
    fun onItemClick(dataModel: HistoryDataWord)
}