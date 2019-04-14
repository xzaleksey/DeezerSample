package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.repository.DeezerRepository
import com.alekseyvalyakin.deezersample.common.ribs.RibActivityInfoProvider
import com.alekseyvalyakin.deezersample.common.utils.rx.subscribeWithErrorLogging
import com.alekseyvalyakin.deezersample.di.schedulers.ThreadConfig
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.dependencies.AlbumChooseListener
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.rib.core.BaseInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [ArtistAlbumsScope].
 *
 */
@RibInteractor
class ArtistAlbumsInteractor : BaseInteractor<ArtistAlbumsPresenter, ArtistAlbumsRouter>() {

    @Inject
    lateinit var presenter: ArtistAlbumsPresenter
    @Inject
    lateinit var ribActivityInfoProvider: RibActivityInfoProvider
    @Inject
    lateinit var repository: DeezerRepository
    @Inject
    lateinit var artistModel: ArtistModel
    @Inject
    lateinit var albumChooseListener: AlbumChooseListener
    @field:[Inject ThreadConfig(ThreadConfig.TYPE.UI)]
    lateinit var uiScheduler: Scheduler

    private val albumsModel = BehaviorRelay.create<ArtistAlbumsModel>()

    //retry policy, error state handle
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        initModel(savedInstanceState)
        observeUiEvents()
        subscribeModelUpdates()
        subscribeRepo()
    }

    private fun initModel(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            albumsModel.accept(savedInstanceState.getSerializable(modelKey) as ArtistAlbumsModel)
        } else if (!albumsModel.hasValue()) {
            albumsModel.accept(ArtistAlbumsModel())
        }
    }

    private fun observeUiEvents() {
        presenter.observeUiEvents()
            .subscribeWithErrorLogging {
                when (it) {
                    is ArtistAlbumsPresenter.UiEvent.ClickBack -> {
                        ribActivityInfoProvider.onBackPressed()
                    }
                    is ArtistAlbumsPresenter.UiEvent.ChooseAlbum -> {
                        albumChooseListener.onAlbumSelected(it.albumModel)
                    }
                }
            }.addToDisposables()
    }

    private fun subscribeModelUpdates() {
        albumsModel.observeOn(uiScheduler)
            .subscribeWithErrorLogging {
                presenter.showUi(it)
            }.addToDisposables()
    }

    private fun subscribeRepo() {
        if (albumsModel.value!!.stateArtist == ArtistAlbumState.SUCCESS) {
            return
        }

        repository.getArtistAlbums(artistModel.id)
            .doOnSubscribe { albumsModel.accept(ArtistAlbumsModel()) }
            .observeOn(uiScheduler)
            .subscribe({
                albumsModel.accept(
                    ArtistAlbumsModel(it, ArtistAlbumState.SUCCESS)
                )
            }) {
                Timber.e(it)
                albumsModel.accept(
                    ArtistAlbumsModel(emptyList(), ArtistAlbumState.FAIL)
                )
            }.addToDisposables()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(modelKey, albumsModel.value)
        super.onSaveInstanceState(outState)
    }

}
