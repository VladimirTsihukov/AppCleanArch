package com.androidapp.appcleanarch.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord
import kotlinx.android.synthetic.main.view_item_holder_history_word.view.*

class AdapterHistory(private val onListenerItemClick: OnListenerItemClickAdapterHistory) :
    RecyclerView.Adapter<AdapterHistory.HolderHistoryWord>() {

    private var listData = mutableListOf<HistoryDataWord>()

    fun setData(newList: List<HistoryDataWord>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderHistoryWord {
        return HolderHistoryWord(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.view_item_holder_history_word,
                    parent,
                    false
                )
        )
    }

    private fun deleteWord(item: Int) {
        listData.removeAt(item)
        notifyItemRemoved(item)
    }

    override fun onBindViewHolder(holder: HolderHistoryWord, position: Int) {
        holder.onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class HolderHistoryWord(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onListenerItemClick.onItemClick(listData[layoutPosition])
            }
            itemView.image_delete_word.setOnClickListener {
                onListenerItemClick.deleteWord(listData[layoutPosition].textHeader)
                deleteWord(layoutPosition)
            }
        }

        fun onBind(data: HistoryDataWord) {
            itemView.tv_history_word.text = data.textHeader
        }
    }
}
