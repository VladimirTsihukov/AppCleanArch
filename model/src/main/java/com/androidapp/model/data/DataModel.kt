package com.androidapp.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel (
        @SerializedName("text")
        val textHeader : String,
        val meanings: List<Meanings>
) : Parcelable