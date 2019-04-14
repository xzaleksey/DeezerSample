package com.alekseyvalyakin.deezersample.common.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.alekseyvalyakin.deezersample.common.utils.image.toBitmap

class DrawableImageHolderImpl(
        private val drawable: Drawable
) : ImageHolder {

    override fun getDrawable(): Drawable? {
        return drawable
    }

    override fun getBitmap(): Bitmap? {
        return drawable.toBitmap()
    }
}