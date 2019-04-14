package com.alekseyvalyakin.deezersample.di.activity

import com.alekseyvalyakin.deezersample.MainActivity

class ActivityListenerImpl(
        private val mainActivity: MainActivity
) : ActivityListener {

    override fun backPress() {
        mainActivity.onBackPressed()
    }
}