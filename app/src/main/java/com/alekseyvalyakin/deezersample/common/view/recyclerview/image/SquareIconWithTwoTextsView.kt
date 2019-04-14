package com.alekseyvalyakin.deezersample.common.view.recyclerview.image

import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.setForegroundSelectableItemBackGround
import com.alekseyvalyakin.deezersample.common.view.squareImageView
import org.jetbrains.anko.*

class SquareIconWithTwoTextsView(context: Context) : _FrameLayout(context) {

    lateinit var imageView: ImageView
    lateinit var textViewPrimary: TextView
    lateinit var textViewSecondary: TextView

    init {
        setForegroundSelectableItemBackGround()

        verticalLayout {
            imageView = squareImageView {
                id = R.id.album_image
            }.lparams(matchParent) {
            }

            textViewPrimary = textView {
                maxLines = 1
                typeface = Typeface.DEFAULT_BOLD
                textColorResource = R.color.colorTextPrimary
                textSizeDimen = R.dimen.common_text_size
            }.lparams(width = matchParent) {
                topMargin = dimen(R.dimen.unit_1)
            }

            textViewSecondary = textView {
                maxLines = 1
                typeface = Typeface.DEFAULT_BOLD
                textColorResource = R.color.colorTextSecondary
                textSizeDimen = R.dimen.hint_text_size
            }.lparams(width = matchParent) {
                topMargin = dimen(R.dimen.unit_half)
            }
        }.lparams(matchParent)

    }
}