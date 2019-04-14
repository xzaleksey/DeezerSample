package com.alekseyvalyakin.deezersample.common.ribs

import com.uber.rib.core.lifecycle.ActivityLifecycleEvent
import io.reactivex.Observable

interface RibActivityInfoProvider {
    fun lifecycle(): Observable<ActivityLifecycleEvent>

    fun <T : ActivityLifecycleEvent> lifecycle(clazz: Class<T>): Observable<T>

    fun onBackPressed()
}