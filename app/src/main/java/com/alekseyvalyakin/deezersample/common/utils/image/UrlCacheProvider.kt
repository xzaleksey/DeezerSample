package com.alekseyvalyakin.deezersample.common.utils.image

import android.graphics.Bitmap
import com.alekseyvalyakin.deezersample.common.image.ImageProvider

//could be added cacheProvider
interface UrlCacheProvider : ImageProvider {
    fun saveToCache(bitmap: Bitmap)

    fun existsInCache(): Boolean

    fun getFilePath(): String
}