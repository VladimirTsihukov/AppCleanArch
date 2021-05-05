package com.androidapp.appcleanarch

import androidx.fragment.app.Fragment
import com.androidapp.appcleanarch.view.main.activity.ActivityMain

class Router {

    var activity: ActivityMain? = null

    fun addFragmentToABackStack(fragment: Fragment, TAG: String) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.container, fragment, TAG)
                .commit()
        }
    }
}