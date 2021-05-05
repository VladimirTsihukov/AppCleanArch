package com.androidapp.historyscreen.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.Router
import com.androidapp.appcleanarch.view.main.fragment.fragmentUI.FragmentDetailWord
import com.androidapp.historyscreen.R
import com.androidapp.historyscreen.injectDependenciesHistory
import com.androidapp.repository.datasource.room.HistoryDataWord
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentHistory : Fragment(R.layout.fragment_history) {

    private val router: Router by getKoin().inject()

    private lateinit var recyclerView: RecyclerView
    private val adapterWord by lazy { AdapterHistory(clickListener) }
    private lateinit var viewModel: ViewModelFragmentHistory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initToolbar()

        recyclerView = view.findViewById(R.id.recycler_fragment_history)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapterWord

    }

    private fun initViewModel() {
        injectDependenciesHistory()

        val model: ViewModelFragmentHistory by viewModel()
        viewModel = model
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
            router.addFragmentToABackStack(FragmentDetailWord.newInstance(dataModel),
                FragmentDetailWord.TAG)
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