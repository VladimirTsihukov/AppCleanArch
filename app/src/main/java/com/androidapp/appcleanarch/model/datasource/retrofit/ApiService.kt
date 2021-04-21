package com.androidapp.appcleanarch.model.datasource.retrofit

import com.androidapp.appcleanarch.model.data.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    suspend fun search(@Query("search") word : String) : List<DataModel>
}