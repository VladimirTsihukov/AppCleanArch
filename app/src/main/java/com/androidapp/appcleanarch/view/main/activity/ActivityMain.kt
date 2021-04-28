package com.androidapp.appcleanarch.view.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.view.main.fragment.OnSearchClickListener
import com.androidapp.appcleanarch.view.main.fragment.fragmentUI.FragmentMain

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentMain.newInstance(), FragmentMain.TAG)
                .commit()
        }
    }

    val searchClick = object : OnSearchClickListener {
        override fun onClick(word: String) {
            supportFragmentManager.findFragmentByTag(FragmentMain.TAG)?.let {
                (it as FragmentMain).searchClickInFragmentDialog(word)
            }
        }
    }
}