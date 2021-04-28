package com.androidapp.appcleanarch.model.datasource.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME_WORD)
@Parcelize
data class HistoryDataWord (
    @field:ColumnInfo(name = "word")
    @PrimaryKey
    val textHeader : String,

    val translation: String?,
    val transcription: String?,
    val imageUrl: String?,
    val soundUrl: String?,
): Parcelable