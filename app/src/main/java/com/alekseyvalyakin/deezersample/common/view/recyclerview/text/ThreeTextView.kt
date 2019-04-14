package com.alekseyvalyakin.deezersample.common.view.recyclerview.text

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.getSelectableItemBackGround
import org.jetbrains.anko.*

class ThreeTextView(context: Context) : _RelativeLayout(context) {

    val textLeft: TextView
    val textPrimary: TextView
    val textSecondary: TextView

    init {
        backgroundResource = getSelectableItemBackGround()
        minimumHeight = dimen(R.dimen.unit_7)
        rightPadding = dimen(R.dimen.common_horizontal_margin)
        gravity = Gravity.CENTER_VERTICAL

        val vPadding = dimen(R.dimen.unit_1)
        val hPadding = dimen(R.dimen.common_horizontal_margin)
        setPadding(hPadding, vPadding, hPadding, vPadding)

        textLeft = textView {
            id = R.id.text_left
            maxLines = 1
            textColorResource = R.color.colorTextSecondary
            textSizeDimen = R.dimen.hint_text_size
        }.lparams {
            centerVertically()
            marginEnd = dimen(R.dimen.common_horizontal_margin)
            alignParentStart()
        }

        textPrimary = textView {
            id = R.id.text_primary
            maxLines = 1
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorTextPrimary
            textSizeDimen = R.dimen.common_text_size
        }.lparams(width = matchParent) {
            endOf(textLeft)
        }

        textSecondary = textView {
            maxLines = 1
            textColorResource = R.color.colorTextSecondary
            textSizeDimen = R.dimen.hint_text_size
        }.lparams(width = matchParent) {
            topMargin = dimen(R.dimen.unit_half)
            endOf(textLeft)
            below(textPrimary)
        }

    }
}