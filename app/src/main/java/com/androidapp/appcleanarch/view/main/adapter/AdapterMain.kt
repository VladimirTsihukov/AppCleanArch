package com.androidapp.appcleanarch.view.main.adapter

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.utils.URL_ADD
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_item_holder.view.*

class AdapterMain(private val onClickItem: OnListenerItemClick) : RecyclerView.Adapter<AdapterMain.HolderMain>() {

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
            itemView.img_play.visibility = View.INVISIBLE
            itemView.img_stop.visibility = View.INVISIBLE
            with(itemView) {
                tv_item_header.text = data.textHeader
                tv_item_transcription.text = itemView.context.getString(R.string.transcription).let {
                    String.format(it, data.meanings[0].transcription)
                }
                tv_item_translation.text = data.meanings[0].translation?.translation
                data.meanings[0].imageUrl?.let { setImg(it) }
                data.meanings[0].soundUrl?.let { workSoundUrl(it) }
            }
        }

        private fun setImg(imgUrl: String) {
            Glide.with(itemView.context)
                .load(URL_ADD + imgUrl)
                .placeholder(R.drawable.ic_img_loading)
                .error(R.drawable.ic_img_error)
                .into(itemView.img_word)
        }

        private fun workSoundUrl(soundUrl: String) {
            itemView.img_play.visibility = View.VISIBLE
            val mediaPlayer = MediaPlayer()
            val audioUrl = URL_ADD + soundUrl
            mediaPlayer.setAudioAttributes(AudioAttributes
                    .Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build()
            )
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepareAsync()

            itemView.img_play.setOnClickListener {
                itemView.img_play.visibility = View.INVISIBLE
                itemView.img_stop.visibility = View.VISIBLE
                mediaPlayer.start()
            }

            itemView.img_stop.setOnClickListener {
                itemView.img_play.visibility = View.VISIBLE
                itemView.img_stop.visibility = View.INVISIBLE
                mediaPlayer.pause()
            }
        }
    }
}