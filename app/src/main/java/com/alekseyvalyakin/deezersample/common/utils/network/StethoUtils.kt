package com.alekseyvalyakin.deezersample.common.utils.network

import android.app.Application
import com.facebook.stetho.Stetho

//could be added stub to release
object StethoUtils {
    fun install(application: Application) {
        Stetho.initialize(
            Stetho.newInitializerBuilder(application)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                .build())
    }
}