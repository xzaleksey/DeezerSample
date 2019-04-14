package com.alekseyvalyakin.deezersample.ribs.root

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Top level view for {@link RootBuilder.RootScope}.
 */
class RootView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle), RootInteractor.RootPresenter {

    init {
        fitsSystemWindows = true
    }

    @Suppress("OverridingDeprecatedMember")
    override fun fitSystemWindows(insets: Rect?): Boolean {
        insets?.left = 0
        insets?.right = 0
        insets?.top = 0
        return super.fitSystemWindows(insets)
    }
}
