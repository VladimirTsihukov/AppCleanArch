package com.androidapp.appcleanarch.utils

import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord
import com.androidapp.model.data.DataModel
import com.androidapp.model.data.Meanings
import com.androidapp.model.data.Translation

fun convertFindWordToEntity(word: String, dataModel: List<DataModel>): HistoryDataWord? {
    val result = dataModel.find { word == it.textHeader }
    result?.let {
        return HistoryDataWord(
            textHeader = result.textHeader,
            translation = result.meanings[0].translation?.translation,
            transcription = result.meanings[0].transcription,
            imageUrl = result.meanings[0].imageUrl,
            soundUrl = result.meanings[0].soundUrl,
        )
    }
    return null
}

fun convertListDataModelOtEntity(dataModel: List<DataModel>): List<HistoryDataWord> {
    return dataModel.map {
        HistoryDataWord(
            textHeader = it.textHeader,
            translation = it.meanings[0].translation?.translation,
            transcription = it.meanings[0].transcription,
            imageUrl = it.meanings[0].imageUrl,
            soundUrl = it.meanings[0].soundUrl,
        )
    }
}

fun convertListEntityToDataModel(dataModel: List<HistoryDataWord>): List<DataModel> {
    return dataModel.map {
        DataModel(
            textHeader = it.textHeader,
            meanings = listOf(
                Meanings(
                    transcription = it.transcription,
                    imageUrl = it.imageUrl,
                    soundUrl = it.soundUrl,
                    translation = Translation(
                        translation = it.translation
                    )
                )
            )
        )
    }
}