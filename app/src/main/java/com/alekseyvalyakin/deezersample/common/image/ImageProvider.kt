package com.alekseyvalyakin.deezersample.common.image

import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import io.reactivex.Observable

interface ImageProvider {
    fun observeImage(): Observable<ImageHolder>

    fun getId(): String

    object Empty : ImageProvider {
        override fun observeImage(): Observable<ImageHolder> {
            return Observable.empty()
        }

        override fun getId(): String {
            return EMPTY_STRING
        }
    }
}