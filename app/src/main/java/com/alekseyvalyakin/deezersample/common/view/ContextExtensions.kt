package com.alekseyvalyakin.deezersample.common.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import org.jetbrains.anko.dip

private const val DEFAULT_STATUS_BAR_HEIGHT_NEW = 24
private const val DEFAULT_STATUS_BAR_HEIGHT_OLD = 25

fun Context.getCompatColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getCompatDrawable(@DrawableRes res: Int): Drawable {
    return ContextCompat.getDrawable(this, res)!!
}

fun Context.getCompatColorState(@ColorRes res: Int): ColorStateList {
    return ContextCompat.getColorStateList(this, res)!!
}

fun Context.getSelectableItemBackGround(): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    return outValue.resourceId
}

fun Context.getSelectableItemBorderless(): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
    return outValue.resourceId
}

fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId)
    }
    val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        dip(DEFAULT_STATUS_BAR_HEIGHT_NEW)
    } else {
        dip(DEFAULT_STATUS_BAR_HEIGHT_OLD)
    }

    return Math.ceil(result.toDouble()).toInt()
}

fun Context.getToolbarHeight(): Int {
    val resourceId = resources.getIdentifier("action_bar_size", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else dip(56)
}

fun Context.getScreenOrientation(): Int {
    return resources.configuration.orientation
}

fun Context.isOrientationLandscape(): Boolean {
    return getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE
}

fun Context.getDisplayMetrics(): DisplayMetrics {
    return resources.displayMetrics
}

fun Context.getDisplayWidth(): Int {
    return getDisplayMetrics().widthPixels
}

fun Context.getDisplaySmallestWidth(): Int {
    val displayMetrics = getDisplayMetrics()
    return if (displayMetrics.widthPixels < displayMetrics.heightPixels) {
        displayMetrics.widthPixels
    } else {
        displayMetrics.heightPixels
    }
}


fun Context.getDividerDrawable(): Drawable {
    val attrs = intArrayOf(android.R.attr.listDivider)

    val styledAttributes = obtainStyledAttributes(attrs)
    val drawable: Drawable = styledAttributes.getDrawable(0)!!
    styledAttributes.recycle()
    return drawable
}