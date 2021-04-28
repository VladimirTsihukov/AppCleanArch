package com.androidapp.appcleanarch.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidapp.appcleanarch.model.datasource.room.HistoryDao
import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord
import kotlinx.coroutines.*

class ViewModelFragmentHistory(private val historyDao: HistoryDao) : ViewModel() {

    val liveDataHistory: MutableLiveData<List<HistoryDataWord>> = MutableLiveData()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->

        }
    )

    init {
        getData()
    }

    fun getData() {
        viewModelCoroutineScope.launch {
            historyDao.getListHistoryEntity()?.let {
                val result = it
                liveDataHistory.postValue(result)
            }
        }
    }

    fun deleteWordInDB(word: String) {
        viewModelCoroutineScope.launch {
            historyDao.deleteWordForName(word)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}