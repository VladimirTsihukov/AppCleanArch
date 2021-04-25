package com.androidapp.appcleanarch.view.main.fragment.fragmentUI

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.AppState
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.model.datasource.retrofit.RetrofitImplementation
import com.androidapp.appcleanarch.utils.network.isOnline
import com.androidapp.appcleanarch.view.base.FragmentBase
import com.androidapp.appcleanarch.view.main.adapter.AdapterMain
import com.androidapp.appcleanarch.view.main.adapter.OnListenerItemClick
import com.androidapp.appcleanarch.view.main.fragment.fragmentDialog.FragmentDialogSearch
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentMain
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentMain : FragmentBase<AppState>(R.layout.fragment_main) {

    override val viewModel by viewModel<ViewModelFragmentMain>()

    private var activityMain: FragmentActivity? = null

    private val adapter: AdapterMain by lazy { AdapterMain(onItemClick) }
    private lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()
    private val iterator = RetrofitImplementation()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.main_rec_view)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        fub_btn_search.setOnClickListener {
            activityMain?.supportFragmentManager?.let { fragmentManager ->
                FragmentDialogSearch.newInstance()
                    .show(fragmentManager, FragmentDialogSearch.TAG)
            }
        }

        viewModel.subscriberLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activityMain = activity
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel?.isEmpty() == true) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    dataModel?.let { adapter.setData(it) }
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

    private val onItemClick = object : OnListenerItemClick {
        override fun onItemClick(dataModel: DataModel) {
            Toast.makeText(activityMain, dataModel.textHeader, Toast.LENGTH_SHORT).show()
        }
    }

    fun searchClickInFragmentDialog (word: String) {
        activityMain?.let {
            if (isOnline(it)) {
                viewModel.getData(word, true)
            } else {
                showAlertDialog()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "FragmentMain"
        fun newInstance() = FragmentMain()
    }
}