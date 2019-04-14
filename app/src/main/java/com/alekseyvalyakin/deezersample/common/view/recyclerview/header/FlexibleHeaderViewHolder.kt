package com.alekseyvalyakin.deezersample.common.view.recyclerview.header

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder

class FlexibleHeaderViewHolder(itemView: HeaderView, mAdapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(itemView, mAdapter) {
    private val textView = itemView.textView

    fun bind(avatarWithTwoLineTextModel: FlexibleHeaderModel) {
        textView.text= avatarWithTwoLineTextModel.primaryText
    }
}
      