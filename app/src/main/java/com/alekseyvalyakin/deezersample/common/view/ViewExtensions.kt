package com.alekseyvalyakin.deezersample.common.view

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.widget.ImageViewCompat
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView


fun View.getString(@StringRes res: Int): String {
    return context.getString(res)
}

fun View.getCompatColor(@ColorRes color: Int): Int {
    return context.getCompatColor(color)
}

fun View.getCompatDrawable(@DrawableRes res: Int): Drawable {
    return context.getCompatDrawable(res)
}

fun ImageView.tintImageRes(@ColorRes res: Int) {
    ImageViewCompat.setImageTintList(this, getCompatColorStateList(res))
}

fun ImageView.tintImage(@ColorInt color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

fun View.getCompatColorStateList(@ColorRes res: Int): ColorStateList {
    return context.getCompatColorState(res)
}

fun View.getSelectableItemBackGround(): Int {
    return context.getSelectableItemBackGround()
}

fun FrameLayout.setForegroundSelectableItemBackGround() {
    foreground = context.getCompatDrawable(getSelectableItemBackGround())
}

fun FrameLayout.setForegroundSelectableItemBackGroundBorderLess() {
    foreground = context.getCompatDrawable(getSelectableItemBorderless())
}

fun View.getSelectableItemBorderless(): Int {
    return context.getSelectableItemBorderless()
}

fun View.getStatusBarHeight(): Int {
    return context.getStatusBarHeight()
}

fun View.dividerDrawable(): Drawable {
    val styledAttributes = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
    val drawable = styledAttributes.getDrawable(0)
    styledAttributes.recycle()
    return drawable!!
}