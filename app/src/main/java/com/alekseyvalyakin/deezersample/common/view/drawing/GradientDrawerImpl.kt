package com.alekseyvalyakin.deezersample.common.view.drawing

import android.content.Context
import android.graphics.*
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.getCompatColor


class GradientDrawerImpl(
    private val offsets: Rect= Rect(),
    context: Context
) : GradientDrawer {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val topColor = context.getCompatColor(R.color.gradient_start_color)
    private val middleColor = context.getCompatColor(R.color.gradient_middle_color)
    private val middleBottomColor = context.getCompatColor(R.color.gradient_middle_bottom_color)
    private val bottomColor = context.getCompatColor(R.color.backgroundColor)

    override fun drawGradient(canvas: Canvas) {
        val left = offsets.left
        val right = canvas.width - offsets.right
        val top = offsets.top
        val bottom = canvas.height - offsets.bottom
        paint.shader = getShader(top.toFloat(), bottom.toFloat())
        canvas.drawRect(Rect(left, top, right, bottom), paint)
    }

    private fun getShader(
        top: Float,
        bottom: Float
    ): Shader {
        return LinearGradient(
            0f,
            top,
            0f,
            bottom,
            intArrayOf(topColor, middleColor, middleBottomColor, bottomColor, bottomColor),
            floatArrayOf(0f, 0.3f, 0.5f, 0.7f, 1.0f),
            Shader.TileMode.CLAMP
        )
    }
}