package com.alekseyvalyakin.deezersample.di.singleton

import com.alekseyvalyakin.deezersample.app.DeezerApp
import com.alekseyvalyakin.deezersample.common.network.api.DeezerApi
import com.alekseyvalyakin.deezersample.common.repository.DeezerRepository
import com.alekseyvalyakin.deezersample.common.repository.DeezerRepositoryImpl
import com.alekseyvalyakin.deezersample.di.schedulers.ThreadConfig
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Singleton


/**
 * Base app module
 */
@Module
class AppModule(private val mApp: DeezerApp) {

    @Provides
    @Singleton
    fun deezerRepository(
        @ThreadConfig(ThreadConfig.TYPE.IO) ioScheduler: Scheduler,
        deezerApi: DeezerApi
    ): DeezerRepository {
        return DeezerRepositoryImpl(ioScheduler, deezerApi)
    }
}
