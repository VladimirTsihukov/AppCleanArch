package com.androidapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meanings(
        val translation: Translation?,
        val imageUrl: String?,
        val soundUrl: String?,
        val transcription: String?,
) : Parcelable
