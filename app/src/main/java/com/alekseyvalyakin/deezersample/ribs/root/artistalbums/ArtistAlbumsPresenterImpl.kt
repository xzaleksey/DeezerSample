package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.resources.ResourcesProviderImpl
import com.alekseyvalyakin.deezersample.common.utils.image.UrlDrawableProviderImpl
import com.alekseyvalyakin.deezersample.common.view.getDisplayWidth
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.recycler.ArtistAlbumFlexibleTypes
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.recycler.ArtistAlbumFlexibleViewModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
import org.jetbrains.anko.dimen

class ArtistAlbumsPresenterImpl(
    private val artistAlbumsView: ArtistAlbumsView,
    private val artistModel: ArtistModel
) : ArtistAlbumsPresenter {

    private val numOfColumns = 2
    private val resourceProvider = ResourcesProviderImpl(artistAlbumsView.context)
    //single element size
    private val size =
        artistAlbumsView.context.getDisplayWidth() / numOfColumns -
                artistAlbumsView.context.dimen(R.dimen.unit_1) * (2 + numOfColumns * 2)
    private val albumQualityChooser = AlbumModel.getAlbumQualityChooser(size)

    init {
        artistAlbumsView.setRecyclerNumberOfColumns(numOfColumns)
    }

    override fun observeUiEvents(): Observable<ArtistAlbumsPresenter.UiEvent> {
        return Observable.merge(
            observeBackClick(),
            observeItemClicks()
        )
    }

    private fun observeItemClicks(): ObservableSource<ArtistAlbumsPresenter.UiEvent.ChooseAlbum> {
        return artistAlbumsView.ovserveItemClicks()
            .filter { it.layoutRes == ArtistAlbumFlexibleTypes.ALBUM }
            .cast(ArtistAlbumFlexibleViewModel::class.java)
            .map { ArtistAlbumsPresenter.UiEvent.ChooseAlbum(it.albumModel) }
    }

    private fun observeBackClick() =
        artistAlbumsView.observeAppbarBackClick().map { ArtistAlbumsPresenter.UiEvent.ClickBack }

    override fun showUi(viewModel: ArtistAlbumsModel) {
        updateItems(viewModel)
        if (viewModel.stateArtist == ArtistAlbumState.LOADING) {
            artistAlbumsView.showLoading()
        } else {
            artistAlbumsView.hideLoading()
        }
    }

    private fun updateItems(viewModel: ArtistAlbumsModel) {
        val items = viewModel.artistResponse
            .asSequence()
            .filter { !albumQualityChooser(it).isNullOrBlank() }
            .map {
                ArtistAlbumFlexibleViewModel(
                    UrlDrawableProviderImpl(
                        albumQualityChooser(it)!!,
                        resourceProvider
                    ), it, artistModel
                )
            }.toList()
        artistAlbumsView.showData(items)
    }
}