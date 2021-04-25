package com.androidapp.appcleanarch.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.DataModel
import kotlinx.android.synthetic.main.view_item_holder.view.*

class AdapterMain(private val onClickItem: OnListenerItemClick) :
    RecyclerView.Adapter<AdapterMain.HolderMain>() {

    private var listData: List<DataModel> = mutableListOf()

    fun setData(data: List<DataModel>) {
        listData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMain {
        return HolderMain(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_holder,
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
                onClickItem.onItemClick(listData[layoutPosition])
            }
        }

        fun onBind(data: DataModel) {
            itemView.tv_item_header.text = data.textHeader
        }
    }
}