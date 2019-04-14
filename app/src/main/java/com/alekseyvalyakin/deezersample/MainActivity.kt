package com.alekseyvalyakin.deezersample

import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.app.DeezerApp
import com.alekseyvalyakin.deezersample.di.activity.ActivityModule
import com.alekseyvalyakin.deezersample.ribs.root.RootBuilder
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter

class MainActivity : RibActivity() {

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {
        val component = DeezerApp.app.getAppComponent()
            .activityComponent(ActivityModule(this))
        component.inject(this)
        return RootBuilder(component).build(parentViewGroup)
    }
}
