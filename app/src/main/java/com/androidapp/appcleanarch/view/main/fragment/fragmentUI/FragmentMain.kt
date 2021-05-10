package com.androidapp.appcleanarch.view.main.fragment.fragmentUI

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.App
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.Router
import com.androidapp.appcleanarch.view.main.adapter.AdapterMain
import com.androidapp.appcleanarch.view.main.adapter.OnListenerItemClickAdapterMain
import com.androidapp.appcleanarch.view.main.fragment.fragmentDialog.FragmentDialogSearch
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentMain
import com.androidapp.core.base.FragmentBase
import com.androidapp.model.data.AppState
import com.androidapp.repository.convertListDataModelOtEntity
import com.androidapp.repository.datasource.retrofit.RetrofitImplementation
import com.androidapp.repository.datasource.room.HistoryDataWord
import com.androidapp.utils.network.OnlineLiveData
import com.androidapp.utils.ui.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

private const val HISTORY_FEATURE_NAME = "historyscreen"
private const val HISTORY_PACKAGE_NAME = "com.androidapp.historyscreen.history.FragmentHistory"
private const val TAG_FRAGMENT_HISTORY = "FragmentHistory"
private const val REG_CODE_SETTING = 789

class FragmentMain : FragmentBase<AppState>(R.layout.fragment_main) {

    //отвечает за установку dynamic feature
    private lateinit var splitInstallManager: SplitInstallManager

    override lateinit var viewModel: ViewModelFragmentMain

    private var activityMain: FragmentActivity? = null

    private val adapter: AdapterMain by lazy { AdapterMain(onItemClick) }
    private lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()
    private val iterator = RetrofitImplementation()
    private val router: Router by getKoin().inject()

    private var isOnline: Boolean = false
    private val onlineLiveData: OnlineLiveData by lazy { OnlineLiveData(App.instance) }

    private val searchFab by viewById<FloatingActionButton>(R.id.fub_btn_search)

    private val snackbar by lazy {
        Snackbar.make(fragment_main_view,
            R.string.snackBar_title_device_is_offline,
            Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.dialog_button_yes) {
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY),
                    REG_CODE_SETTING)
            }
            .setTextColor(Color.RED)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityMain = activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isOnline = onlineLiveData.isNetworkAvailable()
        snowSnackBarNetwork(isOnline)

        initViewModel()
        initToolBar()
        initRecView(view)

        searchFab.setOnClickListener {
            activityMain?.supportFragmentManager?.let { fragmentManager ->
                FragmentDialogSearch.newInstance()
                    .show(fragmentManager, FragmentDialogSearch.TAG)
            }
        }

        viewModel.subscriberLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })

        activityMain?.let {
            onlineLiveData.observe(viewLifecycleOwner, { online ->
                isOnline = online
                snowSnackBarNetwork(isOnline)
            })
        }
    }

    private fun initRecView(view: View) {
        recyclerView = view.findViewById(R.id.main_rec_view)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    private fun snowSnackBarNetwork(online: Boolean) {
        if (!online) snackbar.show() else snackbar.dismiss()
    }

    private fun initViewModel() {
        val model: ViewModelFragmentMain by currentScope.viewModel(this)
        viewModel = model
    }

    private fun initToolBar() {
        tool_bar_fragment_main.inflateMenu(R.menu.menu_fragment_main)
        tool_bar_fragment_main.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_fragment_main_history -> {
                    splitManager()
                    true
                }
                R.id.menu_fragment_main_setting -> {
                    startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY),
                        REG_CODE_SETTING)
                    true
                }
                else -> true
            }
        }
    }

    private fun splitManager() {
        splitInstallManager = SplitInstallManagerFactory.create(App.instance)
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(HISTORY_FEATURE_NAME)
            .build()

        //начинаем скачивать обновление
        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                activityMain?.let {
                    val fragment =
                        Class.forName(HISTORY_PACKAGE_NAME).newInstance() as Fragment
                    it.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.container, fragment, TAG_FRAGMENT_HISTORY)
                        .commit()
                }
            }
            .addOnFailureListener {
                Log.i("TAG", "Couldn't download feature: " + it.message)
                Toast.makeText(activityMain,
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel?.isEmpty() == true) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    dataModel?.let { adapter.setData(convertListDataModelOtEntity(it)) }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    appState.progress?.let {
                        progress_bar_horizontal.progress
                    }
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
            viewModel.getData("hi", isOnline)
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

    private val onItemClick = object : OnListenerItemClickAdapterMain {
        override fun onItemClick(dataModel: HistoryDataWord) {
            router.addFragmentToABackStack(FragmentDetailWord.newInstance(dataModel),
                FragmentDetailWord.TAG)
        }
    }

    fun searchClickInFragmentDialog(word: String) {
        activityMain?.let {
            if (isOnline) {
                viewModel.getData(word, isOnline)
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