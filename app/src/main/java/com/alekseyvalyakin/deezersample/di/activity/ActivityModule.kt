package com.alekseyvalyakin.deezersample.di.activity

import com.alekseyvalyakin.deezersample.MainActivity
import com.alekseyvalyakin.deezersample.common.ribs.RibActivityInfoProvider
import com.alekseyvalyakin.deezersample.common.ribs.RibActivityInfoProviderImpl
import dagger.Module
import dagger.Provides

/**
 * Base app module
 */
@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides
    @ActivityScope
    fun provideRibActivityInfoProvider(): RibActivityInfoProvider {
        return RibActivityInfoProviderImpl(activity)
    }

}
