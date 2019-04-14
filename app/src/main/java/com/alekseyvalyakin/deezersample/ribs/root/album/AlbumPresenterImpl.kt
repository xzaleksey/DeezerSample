package com.alekseyvalyakin.deezersample.ribs.root.album

import android.support.v7.widget.RecyclerView
import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.image.ImageProvider
import com.alekseyvalyakin.deezersample.common.resources.ResourcesProviderImpl
import com.alekseyvalyakin.deezersample.common.utils.image.UrlDrawableProviderImpl
import com.alekseyvalyakin.deezersample.common.utils.rx.subscribeWithErrorLogging
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import com.alekseyvalyakin.deezersample.common.view.getDisplaySmallestWidth
import com.alekseyvalyakin.deezersample.common.view.recyclerview.text.FlexibleTwoLineTextModel
import com.alekseyvalyakin.deezersample.ribs.root.album.recycler.FlexibleGradientModel
import com.alekseyvalyakin.deezersample.ribs.root.album.recycler.FlexibleTrackModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import org.jetbrains.anko.dimen
import org.jetbrains.anko.image
import timber.log.Timber

class AlbumPresenterImpl(
    private val albumView: AlbumView
) : AlbumPresenter {
    private var disposable = Disposables.disposed()
    private val resourcesProvider = ResourcesProviderImpl(albumView.context)
    private val size = albumView.context.getDisplaySmallestWidth() - albumView.context.dimen(R.dimen.unit_7) * 2
    private val gradientSize = size + albumView.context.dimen(R.dimen.unit_7)
    private val qualityChooser = AlbumModel.getAlbumQualityChooser(size)
    private var latestModel: AlbumViewModel? = null

    init {
        albumView.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var offset = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                offset += dy
                val size = Math.max(0, albumView.imageViewSize - offset)
                Timber.d("size $size offset $offset")
                albumView.imageView.layoutParams.height = size
                albumView.imageView.layoutParams.width = size
                albumView.imageView.requestLayout()
                if (size == 0) {
                    val title = latestModel!!.albumModel!!.title
                    albumView.appbar.setTitle(title)
                    albumView.appbar.setBackgroundResource(R.color.backgroundColor)
                } else {
                    albumView.appbar.setTitle(EMPTY_STRING)
                    albumView.appbar.setBackgroundResource(R.color.transparentColor)
                }
            }
        })
    }

    override fun observeUiEvents(): Observable<AlbumPresenter.UiEvent> {
        return albumView.observeBackClicks()
            .doOnDispose { disposable.dispose() }
            .map { AlbumPresenter.UiEvent.BackClick }
    }

    override fun showUi(viewModel: AlbumViewModel) {
        latestModel = viewModel
        val albumModel = viewModel.albumModel

        if (albumModel != null) {
            loadImageUrl(
                UrlDrawableProviderImpl(
                    qualityChooser(albumModel)!!,
                    resourcesProvider
                )
            )
            albumView.setItems(getDefaultItems() + getStartElements(albumModel) + getTracks(albumModel))
        } else {
            albumView.setItems(getDefaultItems())
        }

        if (viewModel.state == AlbumViewModel.State.LOADING) {
            albumView.showLoading()
        } else {
            albumView.hideLoading()
        }
    }

    private fun getStartElements(albumModel: AlbumModel): List<IFlexible<*>> {
        return listOf(
            FlexibleTwoLineTextModel(albumModel.title, albumModel.artist!!.name, albumModel.id, true)
        )
    }

    private fun getDefaultItems() = listOf(FlexibleGradientModel(gradientSize))

    private fun getTracks(albumModel: AlbumModel): List<FlexibleTrackModel> {
        return albumModel.tracks?.data?.mapIndexed { index, trackModel ->
            FlexibleTrackModel(trackModel, (index + 1).toString())
        } ?: emptyList()
    }

    private fun loadImageUrl(imageProvider: ImageProvider) {
        if (albumView.imageView.image == null) {
            disposable.dispose()
            disposable = imageProvider.observeImage()
                .subscribeWithErrorLogging { i ->
                    Glide.with(albumView.imageView)
                        .load(i.getBitmap())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(albumView.imageView)
                }
        }
    }
}