package com.alekseyvalyakin.deezersample.common.view.recyclerview.image

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.getSelectableItemBackGround
import org.jetbrains.anko.*

class IconWithTextView(context: Context) : _LinearLayout(context) {

    val imageView: ImageView
    val textView: TextView

    init {
        orientation = LinearLayout.HORIZONTAL
        backgroundResource = getSelectableItemBackGround()
        minimumHeight = dimen(R.dimen.unit_7)
        rightPadding = dimen(R.dimen.common_horizontal_margin)
        gravity = Gravity.CENTER_VERTICAL

        val vPadding = dimen(R.dimen.unit_1)
        val hPadding = dimen(R.dimen.common_horizontal_margin)
        setPadding(hPadding, vPadding, hPadding, vPadding)

        imageView = imageView {
            id = R.id.artist_avatar
        }.lparams(width = dimen(R.dimen.unit_7), height = dimen(R.dimen.unit_7)) {
            marginEnd = dimen(R.dimen.unit_2)
        }

        textView = textView {
            maxLines = 1
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorTextPrimary
            textSizeDimen = R.dimen.common_text_size
        }.lparams(width = matchParent)

    }
}