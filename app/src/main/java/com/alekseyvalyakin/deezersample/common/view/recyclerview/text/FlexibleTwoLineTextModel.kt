package com.alekseyvalyakin.deezersample.common.view.recyclerview.text

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

open class FlexibleTwoLineTextModel(
    val primaryText: String = EMPTY_STRING,
    val secondaryText: String = EMPTY_STRING,
    val id: String,
    val needDrawDivider: Boolean = false
) : AbstractFlexibleItem<FlexibleTwoTextsViewHolder>() {

    override fun createViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): FlexibleTwoTextsViewHolder {
        val itemView = TwoTextsView(parent.context)
            .apply {
                layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
            }

        return FlexibleTwoTextsViewHolder(
            itemView,
            adapter
        )
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        holder: FlexibleTwoTextsViewHolder,
        position: Int,
        payloads: MutableList<Any?>?
    ) {
        holder.bind(this)
    }

    override fun getLayoutRes(): Int {
        return FlexibleLayoutTypes.TWO_TEXTS
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlexibleTwoLineTextModel

        if (primaryText != other.primaryText) return false
        if (secondaryText != other.secondaryText) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = primaryText.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + secondaryText.hashCode()
        return result
    }

}
