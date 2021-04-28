package com.androidapp.appcleanarch.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.datasource.room.HistoryDataWord
import kotlinx.android.synthetic.main.view_item_holder_word.view.*

class AdapterMain(private val onClickItemAdapterMain: OnListenerItemClickAdapterMain) :
    RecyclerView.Adapter<AdapterMain.HolderMain>() {

    private var listData: List<HistoryDataWord> = mutableListOf()

    fun setData(data: List<HistoryDataWord>) {
        listData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMain {
        return HolderMain(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_holder_word,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderMain, position: Int) {
        holder.onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class HolderMain(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onClickItemAdapterMain.onItemClick(listData[layoutPosition])
            }
        }

        fun onBind(data: HistoryDataWord) {
            itemView.tv_word.text = data.textHeader
            itemView.tv_word_translation.text = data.translation
        }
    }
}