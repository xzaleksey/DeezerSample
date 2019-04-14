package com.alekseyvalyakin.deezersample.common.utils.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.alekseyvalyakin.deezersample.common.image.ImageHolder
import com.alekseyvalyakin.deezersample.common.resources.ResourcesProvider


class BitmapImageHolderImpl(
        private val bitmap: Bitmap,
        private val resourcesProvider: ResourcesProvider
) : ImageHolder {

    override fun getDrawable(): Drawable? {
        return resourcesProvider.bitmapToDrawable(bitmap)
    }

    override fun getBitmap(): Bitmap? {
        return bitmap
    }
}