package com.alekseyvalyakin.deezersample.ribs.root.album.recycler

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.alekseyvalyakin.deezersample.common.view.drawing.GradientDrawerImpl

class GradientView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val gradientDrawer = GradientDrawerImpl(context = context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gradientDrawer.drawGradient(canvas)
    }
}