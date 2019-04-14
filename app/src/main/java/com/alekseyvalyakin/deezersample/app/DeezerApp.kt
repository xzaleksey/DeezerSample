package com.alekseyvalyakin.deezersample.app

import android.app.Application
import com.alekseyvalyakin.deezersample.BuildConfig
import com.alekseyvalyakin.deezersample.app.tree.MyTree
import com.alekseyvalyakin.deezersample.common.utils.network.StethoUtils
import com.alekseyvalyakin.deezersample.di.singleton.AppComponent
import com.alekseyvalyakin.deezersample.di.singleton.AppModule
import com.alekseyvalyakin.deezersample.di.singleton.DaggerAppComponent
import timber.log.Timber


class DeezerApp : Application() {
    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        app = this
        Timber.plant(MyTree())
        initStetho()
    }

    fun getAppComponent(): AppComponent {
        if (app.component == null) {
            app.component = DaggerAppComponent.builder()
                .appModule(getApplicationModule())
                .build()
        }

        return app.component!!
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            StethoUtils.install(this)
        }
    }

    private fun getApplicationModule(): AppModule {
        return AppModule(this)
    }

    companion object {
        lateinit var app: DeezerApp
    }
}