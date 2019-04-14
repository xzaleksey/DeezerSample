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

open class FlexibleAvatarWithOneLineTextModel(
    val primaryText: String = EMPTY_STRING,
    val imageProvider: ImageProvider,
    val id: String
) : AbstractFlexibleItem<FlexibleIconWithTextViewHolder>() {

    override fun createViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): FlexibleIconWithTextViewHolder {
        val itemView = IconWithTextView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
        return FlexibleIconWithTextViewHolder(itemView, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        holder: FlexibleIconWithTextViewHolder,
        position: Int,
        payloads: MutableList<Any?>?
    ) {
        holder.bind(this)
    }

    override fun getLayoutRes(): Int {
        return FlexibleLayoutTypes.ICON_WITH_TEXT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlexibleAvatarWithOneLineTextModel

        if (primaryText != other.primaryText) return false
        if (imageProvider.getId() != other.imageProvider.getId()) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = primaryText.hashCode()
        result = 31 * result + imageProvider.getId().hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

}
