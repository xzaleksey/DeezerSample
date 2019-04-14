package com.alekseyvalyakin.deezersample.common.view

import android.view.ViewManager
import com.alekseyvalyakin.deezersample.common.view.appbar.Appbar
import com.alekseyvalyakin.deezersample.common.view.image.SquareImageView
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.searchInput(theme: Int = 0) = searchInput({}, theme)

inline fun ViewManager.searchInput(init: SearchInputView.() -> Unit = {}) = searchInput(init, 0)

inline fun ViewManager.searchInput(init: SearchInputView.() -> Unit, theme: Int = 0) =
    ankoView(::SearchInputView, theme, init)

inline fun ViewManager.appBar(theme: Int = 0) = appBar({}, theme)

inline fun ViewManager.appBar(init: Appbar.() -> Unit = {}) = appBar(init, 0)

inline fun ViewManager.appBar(init: Appbar.() -> Unit, theme: Int = 0) = ankoView(::Appbar, theme, init)

inline fun ViewManager.squareImageView(theme: Int = 0) = squareImageView({}, theme)

inline fun ViewManager.squareImageView(init: SquareImageView.() -> Unit = {}) = squareImageView(init, 0)

inline fun ViewManager.squareImageView(init: SquareImageView.() -> Unit, theme: Int = 0) = ankoView({
    SquareImageView(it)
}, theme, init)
