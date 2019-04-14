package com.alekseyvalyakin.deezersample.common.view.recyclerview.header

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import com.alekseyvalyakin.deezersample.common.view.recyclerview.FlexibleLayoutTypes
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

open class FlexibleHeaderModel(
    val primaryText: String = EMPTY_STRING
) : AbstractFlexibleItem<FlexibleHeaderViewHolder>() {

    override fun createViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): FlexibleHeaderViewHolder {
        val itemView = HeaderView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
        return FlexibleHeaderViewHolder(itemView, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        holder: FlexibleHeaderViewHolder,
        position: Int,
        payloads: MutableList<Any?>?
    ) {
        holder.bind(this)

    }

    override fun getLayoutRes(): Int {
        return FlexibleLayoutTypes.HEADER
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlexibleHeaderModel

        if (primaryText != other.primaryText) return false

        return true
    }

    override fun hashCode(): Int {
        return primaryText.hashCode()
    }

}
