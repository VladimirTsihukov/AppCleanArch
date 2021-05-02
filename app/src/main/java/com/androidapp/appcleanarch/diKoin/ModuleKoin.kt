package com.androidapp.appcleanarch.diKoin

import androidx.room.Room
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentHistory
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentMain
import com.androidapp.model.data.DataModel
import com.androidapp.repository.datasource.retrofit.RetrofitImplementation
import com.androidapp.repository.datasource.room.DATABASE_NAME
import com.androidapp.repository.datasource.room.HistoryDbWord
import com.androidapp.repository.datasource.room.RoomDataBaseImplementation
import com.androidapp.repository.repository.Repository
import com.androidapp.repository.repository.RepositoryImplementation
import com.androidapp.repository.repository.RepositoryImplementationLocal
import com.androidapp.repository.repository.RepositoryLocal
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

