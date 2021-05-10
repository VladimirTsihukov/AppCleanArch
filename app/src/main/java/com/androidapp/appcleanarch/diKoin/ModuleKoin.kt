package com.androidapp.appcleanarch.diKoin

import androidx.room.Room
import com.androidapp.appcleanarch.Router
import com.androidapp.appcleanarch.view.interactor.InteractorMain
import com.androidapp.appcleanarch.view.main.fragment.fragmentUI.FragmentMain
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
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

//создает зависимость
fun  injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {

    single { Room.databaseBuilder(get(), HistoryDbWord::class.java, DATABASE_NAME).build() }
    single { get<HistoryDbWord>().historyDao() }

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(RetrofitImplementation())
    }

    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }

    single { Router() }
}

val mainScreen = module {
    scope(named<FragmentMain>()) {
        scoped { InteractorMain(get(), get()) }
        viewModel { ViewModelFragmentMain(get()) }
    }
}

