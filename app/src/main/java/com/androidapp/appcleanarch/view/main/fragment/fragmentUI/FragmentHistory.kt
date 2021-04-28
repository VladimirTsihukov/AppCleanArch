package com.androidapp.appcleanarch.view.main.fragment.fragmentUI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord
import com.androidapp.appcleanarch.view.main.adapter.AdapterHistory
import com.androidapp.appcleanarch.view.main.adapter.OnListenerItemClickAdapterHistory
import com.androidapp.appcleanarch.view.viewModel.ViewModelFragmentHistory
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentHistory : Fragment(R.layout.fragment_history) {

    private lateinit var recyclerView: RecyclerView
    private val adapterWord by lazy { AdapterHistory(clickListener) }
    private val viewModel by viewModel<ViewModelFragmentHistory>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        recyclerView = view.findViewById(R.id.recycler_fragment_history)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapterWord

        viewModel.liveDataHistory.observe(viewLifecycleOwner, { liveData ->
            liveData?.let {
                adapterWord.setData(it)
            }
        })
    }

    private fun initToolbar() {
        toolbar_fragment_history.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar_fragment_history.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private val clickListener = object : OnListenerItemClickAdapterHistory {
        override fun onItemClick(dataModel: HistoryDataWord) {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(
                        R.id.container,
                        FragmentDetailWord.newInstance(dataModel),
                        FragmentDetailWord.TAG
                    )
                    .commit()
            }
        }

        override fun deleteWord(word: String) {
            viewModel.deleteWordInDB(word)
        }
    }

    companion object {
        fun newInstance() = FragmentHistory()
        const val TAG = "FragmentHistory"
    }
}