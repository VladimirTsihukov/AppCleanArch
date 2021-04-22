package com.androidapp.appcleanarch.diKoin

import com.androidapp.appcleanarch.di.NAME_LOCAL
import com.androidapp.appcleanarch.di.NAME_REMOTE
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.retrofit.RetrofitImplementation
import com.androidapp.appcleanarch.model.datasource.room.RoomDataBaseImplementation
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.model.repository.RepositoryImplementation
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import com.androidapp.appcleanarch.view.viewModel.ViewModelMain
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {

    factory {
        InteractorMain(get(named(NAME_REMOTE)), get(named(NAME_LOCAL)))
    }

    viewModel {
        ViewModelMain(get())
    }
}

