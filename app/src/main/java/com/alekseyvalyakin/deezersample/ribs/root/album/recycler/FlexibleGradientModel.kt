package com.alekseyvalyakin.deezersample.ribs.root.album.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import org.jetbrains.anko.matchParent

class FlexibleGradientModel(val size: Int) : AbstractFlexibleItem<FlexibleGradientViewHolder>() {


    override fun createViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): FlexibleGradientViewHolder {
        val itemView = GradientView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, size)
        }
        return FlexibleGradientViewHolder(itemView, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<out IFlexible<*>>?,
        holder: FlexibleGradientViewHolder,
        position: Int,
        payloads: MutableList<Any?>?
    ) {
        holder.bind(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return this.size == (other as FlexibleGradientModel).size
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        return 31 * result + layoutRes.hashCode()
    }

    override fun getLayoutRes() = AlbumFlexibleTypes.GRADIENT
}