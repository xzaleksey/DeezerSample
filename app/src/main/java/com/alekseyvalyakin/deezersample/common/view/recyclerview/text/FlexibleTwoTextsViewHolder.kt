package com.alekseyvalyakin.deezersample.common.view.recyclerview.text

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder

class FlexibleTwoTextsViewHolder(itemView: TwoTextsView, mAdapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(itemView, mAdapter) {
    private val textView = itemView.textViewPrimary
    private val textViewSecondary = itemView.textViewSecondary
    private val divider = itemView.divider

    fun bind(model: FlexibleTwoLineTextModel) {
        textView.text = model.primaryText
        textViewSecondary.text = model.secondaryText
        divider.visibility = if (model.needDrawDivider) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }


}
      