package com.androidapp.appcleanarch.view.main.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.App
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.retrofit.RetrofitImplementation
import com.androidapp.appcleanarch.view.base.ActivityBas
import com.androidapp.appcleanarch.view.main.adapter.AdapterMain
import com.androidapp.appcleanarch.view.main.adapter.OnListenerItemClick
import com.androidapp.appcleanarch.view.main.fragment.FragmentDialogSearch
import com.androidapp.appcleanarch.view.main.fragment.OnSearchClickListener
import com.androidapp.appcleanarch.view.viewModel.ViewModelMain
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_error.*
import javax.inject.Inject

class ActivityMain : ActivityBas<AppState>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter: AdapterMain by lazy { AdapterMain(onItemClick) }
    private lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()
    private val iterator = RetrofitImplementation()

    override val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ViewModelMain::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        recyclerView = findViewById(R.id.main_rec_view)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        fub_btn_search.setOnClickListener {
            FragmentDialogSearch.newInstance()
                .show(supportFragmentManager, FragmentDialogSearch.TAG)
        }

        viewModel.subscriberLiveData().observe(this, {
            renderData(it)
        })
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    adapter.setData(dataModel)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> showErrorScreen(appState.error.message)
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        tv_error.text = error ?: getString(R.string.undefined_error)
        btn_reload.setOnClickListener {
            viewModel.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        layout_success.visibility = View.VISIBLE
        layout_loading.visibility = View.GONE
        layout_error.visibility = View.GONE
    }

    private fun showViewLoading() {
        layout_success.visibility = View.GONE
        layout_loading.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
    }

    private fun showViewError() {
        layout_success.visibility = View.GONE
        layout_loading.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
    }

    val searchClick = object : OnSearchClickListener {
        override fun onClick(word: String) {
            viewModel.getData(word, true)
        }
    }

    private val onItemClick = object : OnListenerItemClick {
        override fun onItemClick(dataModel: DataModel) {
            Toast.makeText(this@ActivityMain, dataModel.textHeader, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}