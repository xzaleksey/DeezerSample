package com.alekseyvalyakin.deezersample.common.view.recyclerview.image

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.common.image.ImageProvider
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import com.alekseyvalyakin.deezersample.common.view.recyclerview.FlexibleLayoutTypes
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

open class FlexibleSquareImageWithTwoLineTextModel(
    val primaryText: String = EMPTY_STRING,
    val secondaryText: String = EMPTY_STRING,
    val imageProvider: ImageProvider,
    val id: String
) : AbstractFlexibleItem<FlexibleSquareIconWithTwoTextsViewHolder>() {

    override fun createViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): FlexibleSquareIconWithTwoTextsViewHolder {
        val itemView = SquareIconWithTwoTextsView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }

        return FlexibleSquareIconWithTwoTextsViewHolder(itemView, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        holder: FlexibleSquareIconWithTwoTextsViewHolder,
        position: Int,
        payloads: MutableList<Any?>?
    ) {
        holder.bind(this)
    }

    override fun getLayoutRes(): Int {
        return FlexibleLayoutTypes.SQUARE_ICON_WITH_TWO_TEXTS
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlexibleSquareImageWithTwoLineTextModel

        if (primaryText != other.primaryText) return false
        if (secondaryText != other.secondaryText) return false
        if (imageProvider.getId() != other.imageProvider.getId()) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = primaryText.hashCode()
        result = 31 * result + imageProvider.getId().hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + secondaryText.hashCode()
        return result
    }

}
