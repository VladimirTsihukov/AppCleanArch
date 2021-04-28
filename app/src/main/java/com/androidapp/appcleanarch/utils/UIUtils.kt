package com.androidapp.appcleanarch.utils

import android.app.AlertDialog
import android.content.Context
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.data.Meanings
import com.androidapp.appcleanarch.model.data.Translation
import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord

fun getAlertDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
        .setMessage(R.string.dialog_message_device_is_offline)
        .setTitle(R.string.dialog_title_device_is_offline)
        .setCancelable(true)
        .setPositiveButton(R.string.dialog_button_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
}

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