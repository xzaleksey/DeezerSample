package com.alekseyvalyakin.deezersample.common.network.di

import com.alekseyvalyakin.deezersample.common.network.api.DeezerApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Base app module
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideDefaultGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit2(builder: Retrofit.Builder): Retrofit {
        return builder
            .baseUrl("https://api.deezer.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit2Builder(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideOkClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        return builder.build()
    }

    @Provides
    @Singleton
    fun deezerApi(retrofit: Retrofit): DeezerApi {
        return retrofit.create<DeezerApi>(DeezerApi::class.java)
    }
}
