package com.androidapp.appcleanarch.view.main.adapter

import com.androidapp.appcleanarch.model.data.DataModel

interface OnListenerItemClick {
    fun onItemClick(dataModel: DataModel)
}