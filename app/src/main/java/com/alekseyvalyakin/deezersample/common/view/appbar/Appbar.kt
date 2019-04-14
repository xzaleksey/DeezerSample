package com.alekseyvalyakin.deezersample.common.view.appbar

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.getSelectableItemBorderless
import com.alekseyvalyakin.deezersample.common.view.tintImageRes
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import org.jetbrains.anko.*

class Appbar(context: Context) : _RelativeLayout(context) {

    private val title: TextView
    private val leftIcon: ImageView

    init {
        leftIcon = imageView {
            padding = dimen(R.dimen.unit_2)
            backgroundResource = getSelectableItemBorderless()
            tintImageRes(R.color.iconsColor)
            imageResource = R.drawable.ic_arrow_back
        }.lparams(getWidgetHeight(), getWidgetHeight()) {

        }

        title = textView {
            textSizeDimen = R.dimen.appbar_title_size
        }.lparams {
            marginStart = dimen(R.dimen.unit_9)
            centerVertically()
        }
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setLeftClickListener(clickListener: OnClickListener) {
        leftIcon.setOnClickListener(clickListener)
    }

    fun observeLeftButtonClick(): Observable<Any> {
        return RxView.clicks(leftIcon)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            View.MeasureSpec.makeMeasureSpec(
                getWidgetHeight(),
                View.MeasureSpec.EXACTLY
            )
        )
    }

    private fun getWidgetHeight() = dimen(R.dimen.unit_7)
}