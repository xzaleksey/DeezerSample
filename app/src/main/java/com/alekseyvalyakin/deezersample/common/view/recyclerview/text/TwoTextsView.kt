package com.alekseyvalyakin.deezersample.common.view.recyclerview.text

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.dividerDrawable
import com.alekseyvalyakin.deezersample.common.view.getSelectableItemBackGround
import org.jetbrains.anko.*

class TwoTextsView(context: Context) : _LinearLayout(context) {

    val textViewPrimary: TextView
    val textViewSecondary: TextView
    val divider: View

    init {
        orientation = VERTICAL
        backgroundResource = getSelectableItemBackGround()

        textViewPrimary = textView {
            maxLines = 1
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorTextPrimary
            textSizeDimen = R.dimen.common_text_size
        }.lparams(width = matchParent) {
            topMargin = dimen(R.dimen.unit_1)
            marginStart = dimen(R.dimen.common_horizontal_margin)
            marginEnd = dimen(R.dimen.common_horizontal_margin)
        }

        textViewSecondary = textView {
            maxLines = 1
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorTextSecondary
            textSizeDimen = R.dimen.hint_text_size
        }.lparams(width = matchParent) {
            topMargin = dimen(R.dimen.unit_half)
            marginStart = dimen(R.dimen.common_horizontal_margin)
            marginEnd = dimen(R.dimen.common_horizontal_margin)
        }

        divider = view {
            background = dividerDrawable()
            visibility = View.INVISIBLE
        }.lparams(matchParent, dimen(R.dimen.unit_0125)){
            topMargin = dimen(R.dimen.unit_1)
        }

    }
}