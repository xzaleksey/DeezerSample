package com.alekseyvalyakin.deezersample.di.singleton

import com.alekseyvalyakin.deezersample.common.network.di.NetworkModule
import com.alekseyvalyakin.deezersample.di.activity.ActivityComponent
import com.alekseyvalyakin.deezersample.di.activity.ActivityModule
import com.alekseyvalyakin.deezersample.di.schedulers.SchedulersModule
import dagger.Component
import javax.inject.Singleton

/**
 * Base app component
 */
@Singleton
@Component(modules = [AppModule::class, SchedulersModule::class, NetworkModule::class])
interface AppComponent : SingletonDependencyProvider {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}
