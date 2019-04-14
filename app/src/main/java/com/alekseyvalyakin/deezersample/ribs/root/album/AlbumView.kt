package com.alekseyvalyakin.deezersample.ribs.root.album

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.ProgressBar
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.view.appBar
import com.alekseyvalyakin.deezersample.common.view.appbar.Appbar
import com.alekseyvalyakin.deezersample.common.view.getStatusBarHeight
import com.alekseyvalyakin.deezersample.common.view.squareImageView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Top level view for {@link AlbumBuilder.AlbumScope}.
 */
class AlbumView constructor(context: Context) :
    _RelativeLayout(context) {

    val appbar: Appbar
    var imageView: ImageView
    val recyclerView: RecyclerView
    var imageViewSize: Int = 0
    private val flexibleAdapter = FlexibleAdapter<IFlexible<*>>(emptyList())
    private var progressBar: ProgressBar? = null

    init {
        setWillNotDraw(false)

        view {
            backgroundResource = R.color.backgroundColor
            id = R.id.status_bar
        }.lparams(matchParent, getStatusBarHeight())

        recyclerView = recyclerView {
            backgroundResource = R.color.backgroundColor
            layoutManager = LinearLayoutManager(context)
            adapter = flexibleAdapter
        }.lparams(matchParent, matchParent) {
            below(R.id.status_bar)
        }

        appbar = appBar { id = R.id.appbar }
            .lparams(matchParent) {
                below(R.id.status_bar)
            }

        imageView = squareImageView {

        }.lparams(matchParent) {
            centerHorizontally()
            marginStart = dimen(R.dimen.unit_7)
            marginEnd = dimen(R.dimen.unit_7)
            below(R.id.appbar)
        }
    }

    fun setItems(items: List<IFlexible<*>>) {
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (imageViewSize == 0) {
            imageViewSize = imageView.measuredWidth
        }
    }

    fun observeBackClicks(): Observable<Any> = appbar.observeLeftButtonClick()

}
