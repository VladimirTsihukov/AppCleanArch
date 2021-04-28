package com.androidapp.appcleanarch.diKoin

import androidx.room.Room
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.retrofit.RetrofitImplementation
import com.androidapp.appcleanarch.model.datasource.room.DATABASE_NAME
import com.androidapp.appcleanarch.model.datasource.room.HistoryDbWord
import com.androidapp.appcleanarch.model.datasource.room.RoomDataBaseImplementation
import com.androidapp.appcleanarch.model.repository.Repository
import com.androidapp.appcleanarch.model.repository.RepositoryImplementation
import com.androidapp.appcleanarch.model.repository.RepositoryImplementationLocal
import com.androidapp.appcleanarch.model.repository.RepositoryLocal
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentHistory
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentMain
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDbWord::class.java, DATABASE_NAME).build() }
    single { get<HistoryDbWord>().historyDao() }

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(RetrofitImplementation())
    }

    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {

    factory {
        InteractorMain(get(), get())
    }

    viewModel {
        ViewModelFragmentMain(get())
    }

    viewModel {
        ViewModelFragmentHistory(get())
    }
}

