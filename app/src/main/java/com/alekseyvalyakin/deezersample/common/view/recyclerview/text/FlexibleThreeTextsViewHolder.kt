package com.alekseyvalyakin.deezersample.common.view.recyclerview.text

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder

class FlexibleThreeTextsViewHolder(itemView: ThreeTextView, mAdapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(itemView, mAdapter) {
    private val textLeft = itemView.textLeft
    private val textPrimary = itemView.textPrimary
    private val textSecondary = itemView.textSecondary

    fun bind(avatarWithTwoLineTextModel: FlexibleThreeTextModel) {
        textLeft.text = avatarWithTwoLineTextModel.leftText
        textPrimary.text = avatarWithTwoLineTextModel.primaryText
        textSecondary.text = avatarWithTwoLineTextModel.secondaryText
    }
}
      