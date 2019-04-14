package com.alekseyvalyakin.deezersample.di.singleton


import com.alekseyvalyakin.deezersample.common.repository.DeezerRepository
import com.alekseyvalyakin.deezersample.di.schedulers.ThreadConfig
import io.reactivex.Scheduler

interface SingletonDependencyProvider {
    @ThreadConfig(ThreadConfig.TYPE.UI)
    fun provideUiScheduler(): Scheduler

    @ThreadConfig(ThreadConfig.TYPE.IO)
    fun provideIoScheduler(): Scheduler

    @ThreadConfig(ThreadConfig.TYPE.COMPUTATATION)
    fun provideCompScheduler(): Scheduler

    fun provideDeezerRepo(): DeezerRepository
}