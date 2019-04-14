package com.alekseyvalyakin.deezersample.common.resources

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import com.alekseyvalyakin.deezersample.common.utils.strings.UNDEFINED
import com.alekseyvalyakin.deezersample.common.view.getCompatColor
import timber.log.Timber

// could be provided via dagger
interface ResourcesProvider {
    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int

    fun getString(@StringRes stringRes: Int): String

    fun getString(@StringRes stringRes: Int, vararg args: Any): String

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable

    fun getBitmap(drawableRes: Int): Bitmap?

    fun bitmapToDrawable(bitmap: Bitmap): Drawable

    fun getContext(): Context
}

class ResourcesProviderImpl(private val c: Context) : ResourcesProvider {

    override fun getString(stringRes: Int, vararg args: Any): String {
        try {
            return c.getString(stringRes, *args)
        } catch (ignored: Exception) {
            Timber.e(ignored)
        }
        return UNDEFINED
    }

    override fun getContext(): Context {
        return c
    }

    override fun getString(stringRes: Int): String {
        try {
            return c.getString(stringRes)
        } catch (ignored: Exception) {
            Timber.e(ignored)
        }
        return UNDEFINED
    }

    override fun getColor(colorRes: Int): Int {
        try {
            return ContextCompat.getColor(c, colorRes)
        } catch (ignored: Exception) {
            Timber.e(ignored)
        }
        return 0
    }

    override fun getDrawable(drawableRes: Int): Drawable {
        try {
            return AppCompatResources.getDrawable(c, drawableRes)!!
        } catch (ignored: Exception) {
            Timber.e(ignored)
        }
        return ColorDrawable(c.getCompatColor(android.R.color.transparent))
    }

    override fun getBitmap(drawableRes: Int): Bitmap? {
        try {
            return BitmapFactory.decodeResource(c.resources, drawableRes)
        } catch (ignored: Exception) {
            Timber.e(ignored)
        }
        return null
    }

    override fun bitmapToDrawable(bitmap: Bitmap): Drawable {
        return BitmapDrawable(c.resources, bitmap)
    }

}