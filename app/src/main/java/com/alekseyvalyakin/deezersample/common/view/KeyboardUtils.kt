package com.alekseyvalyakin.deezersample.common.view

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager

@JvmOverloads
fun Activity.hideKeyboard(delay: Long = 10L) {
    val view = currentFocus
    view?.postDelayed({
        if (!view.isAttachedToWindow) {
            return@postDelayed
        }
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }, delay)
}

@JvmOverloads
fun View.hideKeyboard(delay: Long = 10L) {
    context.hideKeyboard(delay)
}

@JvmOverloads
fun Context.hideKeyboard(delay: Long = 10L) {
    when {
        this is Activity -> this.hideKeyboard(delay)
        this is ContextThemeWrapper -> baseContext?.hideKeyboard(delay)
    }
}