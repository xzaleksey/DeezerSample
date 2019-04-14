package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.SearchInputView
import com.alekseyvalyakin.deezersample.common.view.getStatusBarHeight
import com.alekseyvalyakin.deezersample.common.view.searchInput
import com.jakewharton.rxrelay2.PublishRelay
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Top level view for {@link ArtistSearchBuilder.ArtistSearchScope}.
 */
class ArtistSearchView constructor(context: Context) : _LinearLayout(context) {

    private val searchInputView: SearchInputView
    private val recyclerView: RecyclerView
    private val flexibleAdapter = FlexibleAdapter<IFlexible<*>>(emptyList())
    private val itemClicks = PublishRelay.create<IFlexible<*>>()

    init {
        backgroundResource = R.color.backgroundColor
        topPadding = getStatusBarHeight() + dimen(R.dimen.unit_1)
        orientation = VERTICAL

        searchInputView = searchInput {

        }.lparams(matchParent) {
            leftMargin = dimen(R.dimen.common_horizontal_margin)
            rightMargin = dimen(R.dimen.common_horizontal_margin)
        }

        recyclerView = recyclerView {
            id = R.id.recycler_view
            layoutManager = LinearLayoutManager(context)
            adapter = flexibleAdapter
        }.lparams(matchParent, matchParent)

        flexibleAdapter.mItemClickListener =
            FlexibleAdapter.OnItemClickListener { position ->
                flexibleAdapter.getItem(position)?.run {
                    itemClicks.accept(this)
                    return@OnItemClickListener true
                } ?: return@OnItemClickListener false
            }
    }

    fun showData(items: List<IFlexible<*>>) {
        flexibleAdapter.updateDataSet(items, true)
    }

    fun observeItemClicks() = itemClicks.hide()!!

    fun observeInput() = searchInputView.observeSearchInput()

    // here could be error stub
    fun showError(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun setSearchValue(text: String) {
        searchInputView.setText(text)
    }

}
