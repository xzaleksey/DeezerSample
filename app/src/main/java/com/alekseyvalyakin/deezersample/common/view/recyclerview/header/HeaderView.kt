package com.alekseyvalyakin.deezersample.common.view.recyclerview.header

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.getSelectableItemBackGround
import org.jetbrains.anko.*

class HeaderView(context: Context) : _FrameLayout(context) {

    val textView: TextView

    init {
        backgroundResource = getSelectableItemBackGround()
        minimumHeight = dimen(R.dimen.unit_7)
        rightPadding = dimen(R.dimen.common_horizontal_margin)

        val vPadding = dimen(R.dimen.unit_2)
        val hPadding = dimen(R.dimen.common_horizontal_margin)
        setPadding(hPadding, vPadding, hPadding, vPadding)

        textView = textView {
            maxLines = 1
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorTextPrimary
            textSizeDimen = R.dimen.common_text_size
        }.lparams(width = matchParent) {
            gravity = Gravity.CENTER_VERTICAL
        }

    }
}