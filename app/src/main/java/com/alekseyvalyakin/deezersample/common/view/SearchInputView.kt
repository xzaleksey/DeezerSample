package com.alekseyvalyakin.deezersample.common.view

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import org.jetbrains.anko.*

class SearchInputView constructor(
    context: Context
) : _RelativeLayout(context) {

    private val clearView: View
    private lateinit var editText: EditText

    init {
        backgroundResource = R.drawable.search_input_background

        clearView = imageView {
            id = R.id.right_icon
            padding = dimen(R.dimen.unit_2)
            imageResource = R.drawable.ic_close
            backgroundResource = getSelectableItemBorderless()
            setOnClickListener {
                editText.setText(EMPTY_STRING)
            }
        }.lparams(getIconWidgetSize(), getIconWidgetSize()) {
            alignParentEnd()
        }

        imageView {
            padding = dimen(R.dimen.unit_2)
            imageResource = R.drawable.ic_search
            id = R.id.left_icon
            backgroundResource = getSelectableItemBorderless()
        }.lparams(getIconWidgetSize(), getIconWidgetSize()) {
            alignParentStart()
        }

        editText = editText {
            id = R.id.search_input
            backgroundColor = Color.TRANSPARENT
            hint = resources.getString(R.string.search)
            textColor = getCompatColor(R.color.colorTextPrimaryInverse)
            textSizeDimen = R.dimen.common_text_size
            singleLine = true
            imeOptions = EditorInfo.IME_ACTION_SEARCH

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                false
            }
        }.lparams(matchParent, wrapContent) {
            centerVertically()
            endOf(R.id.left_icon)
            startOf(R.id.right_icon)
        }
    }

    fun setText(text: String) {
        editText.setText(text)
    }

    fun observeSearchInput(): Observable<CharSequence> {
        return RxTextView.textChanges(editText).doOnNext { s ->
            if (s.isNullOrEmpty()) {
                clearView.visibility = View.GONE
            } else {
                clearView.visibility = View.VISIBLE
            }
        }
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

    private fun getWidgetHeight() = getIconWidgetSize()

    private fun getIconWidgetSize() = dimen(R.dimen.unit_7)
}