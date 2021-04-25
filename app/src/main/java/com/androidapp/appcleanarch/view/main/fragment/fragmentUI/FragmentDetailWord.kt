package com.androidapp.appcleanarch.view.main.fragment.fragmentUI

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.model.data.DataModel
import com.androidapp.appcleanarch.utils.URL_ADD
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_word.*

class FragmentDetailWord : Fragment(R.layout.fragment_detail_word) {

    private var dataModel: DataModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        dataModel = arguments?.getParcelable(KEY_PARCELABLE) as? DataModel
        dataModel?.let {
            viewInit(it)
        }
    }

    private fun initToolBar() {
        tool_bar_fragment_detail.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        tool_bar_fragment_detail.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
    
    private fun viewInit(data: DataModel) {
        img_play.visibility = View.INVISIBLE
        img_stop.visibility = View.INVISIBLE

        tv_item_header.text = data.textHeader

        tv_item_transcription.text =
            context?.getString(R.string.transcription).let {
                String.format(it ?: "", data.meanings[0].transcription)
            }
        tv_item_translation.text = data.meanings[0].translation?.translation
        data.meanings[0].imageUrl?.let { setImg(it) }
        data.meanings[0].soundUrl?.let { workSoundUrl(it) }
    }

    private fun setImg(imgUrl: String) {
        context?.let {
            Glide.with(it)
                .load(URL_ADD + imgUrl)
                .placeholder(R.drawable.ic_img_loading)
                .error(R.drawable.ic_img_error)
                .into(img_word)
        }
    }

    private fun workSoundUrl(soundUrl: String) {
        img_play.visibility = View.VISIBLE
        val mediaPlayer = MediaPlayer()
        val audioUrl = URL_ADD + soundUrl
        mediaPlayer.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                .build()
        )
        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepareAsync()

        img_play.setOnClickListener {
            img_play.visibility = View.INVISIBLE
            img_stop.visibility = View.VISIBLE
            mediaPlayer.start()
        }

        img_stop.setOnClickListener {
            img_play.visibility = View.VISIBLE
            img_stop.visibility = View.INVISIBLE
            mediaPlayer.pause()
        }
    }

    companion object {
        fun newInstance(data: DataModel): FragmentDetailWord {
            val args = Bundle()
            args.putParcelable(KEY_PARCELABLE, data)
            return FragmentDetailWord().apply { arguments = args }
        }

        const val TAG = "FragmentDetailWord"
        private const val KEY_PARCELABLE = "FragmentDetailWord.KEY_PARCELABLE"
    }
}