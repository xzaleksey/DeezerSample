package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.appBar
import com.alekseyvalyakin.deezersample.common.view.appbar.Appbar
import com.alekseyvalyakin.deezersample.common.view.getStatusBarHeight
import com.alekseyvalyakin.deezersample.common.view.getString
import com.alekseyvalyakin.deezersample.common.view.recyclerview.decoration.ItemOffsetDecoration
import com.jakewharton.rxrelay2.PublishRelay
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Top level view for {@link ArtistAlbumsBuilder.ArtistAlbumsScope}.
 */
class ArtistAlbumsView constructor(
    context: Context
) : _RelativeLayout(context) {

    private val appbar: Appbar
    private val flexibleAdapter = FlexibleAdapter<IFlexible<*>>(emptyList())
    private val recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null
    private val itemClicks = PublishRelay.create<IFlexible<*>>()

    init {
        backgroundResource = R.color.backgroundColor
        topPadding = getStatusBarHeight()
        appbar = appBar {
            id = R.id.appbar
            setTitle(getString(R.string.albums))
        }.lparams(matchParent)

        recyclerView = recyclerView {
            id = R.id.recycler_view
            padding = dimen(R.dimen.unit_1)
            adapter = flexibleAdapter
            addItemDecoration(ItemOffsetDecoration(dimen(R.dimen.unit_1)))
        }.lparams(matchParent, matchParent) {
            below(appbar)
        }
        flexibleAdapter.mItemClickListener =
            FlexibleAdapter.OnItemClickListener { position ->
                flexibleAdapter.getItem(position)?.run {
                    itemClicks.accept(this)
                    return@OnItemClickListener true
                } ?: return@OnItemClickListener false
            }
    }

    fun ovserveItemClicks() = itemClicks.hide()!!

    fun setRecyclerNumberOfColumns(count: Int) {
        recyclerView.layoutManager = GridLayoutManager(context, count)
    }

    fun observeAppbarBackClick(): Observable<Any> {
        return appbar.observeLeftButtonClick()
    }

    fun showData(items: List<IFlexible<*>>) {
        flexibleAdapter.updateDataSet(items, true)
    }

    fun showLoading() {
        if (progressBar == null) {
            progressBar = progressBar {}.lparams {
                centerInParent()
            }
        }
    }

    fun hideLoading() {
        progressBar?.run {
            removeView(this)
            progressBar = null
        }
    }
}
