package com.alekseyvalyakin.deezersample.common.ribs

import com.uber.rib.core.RibActivity
import com.uber.rib.core.lifecycle.ActivityLifecycleEvent
import io.reactivex.Observable

class RibActivityInfoProviderImpl(
    private val activity: RibActivity
) : RibActivityInfoProvider {


    override fun lifecycle(): Observable<ActivityLifecycleEvent> {
        return activity.lifecycle()
    }

    override fun <T : ActivityLifecycleEvent> lifecycle(clazz: Class<T>): Observable<T> {
        return activity.lifecycle(clazz)
    }

    override fun onBackPressed() {
        activity.onBackPressed()
    }

}