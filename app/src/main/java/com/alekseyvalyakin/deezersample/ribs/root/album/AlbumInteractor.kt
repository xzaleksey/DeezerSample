package com.alekseyvalyakin.deezersample.ribs.root.album

import com.alekseyvalyakin.deezersample.common.repository.DeezerRepository
import com.alekseyvalyakin.deezersample.common.ribs.RibActivityInfoProvider
import com.alekseyvalyakin.deezersample.common.utils.rx.subscribeWithErrorLogging
import com.alekseyvalyakin.deezersample.di.schedulers.ThreadConfig
import com.uber.rib.core.BaseInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [AlbumScope].
 *
 */

// Interactor could be tested
@RibInteractor
class AlbumInteractor : BaseInteractor<AlbumPresenter, AlbumRouter>() {

    @Inject
    lateinit var presenter: AlbumPresenter
    @Inject
    lateinit var ribActivityInfoProvider: RibActivityInfoProvider
    @Inject
    lateinit var repo: DeezerRepository
    @Inject
    lateinit var albumId: String
    @field:[Inject ThreadConfig(ThreadConfig.TYPE.UI)]
    lateinit var uiScheduler: Scheduler

    // need to add restore state, retry policy
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        subscribeRepo()
        subscribeUiEvents()
    }

    private fun subscribeRepo() {
        repo.getAlbumModel(albumId)
            .observeOn(uiScheduler)
            .doOnSubscribe { presenter.showUi(AlbumViewModel(state = AlbumViewModel.State.LOADING)) }
            .subscribe({
                presenter.showUi(AlbumViewModel(it, AlbumViewModel.State.SUCCESS))
                Timber.d("Got response $it")
            }) {
                Timber.e(it)
                presenter.showUi(AlbumViewModel(state = AlbumViewModel.State.FAILED))
            }.addToDisposables()
    }


    private fun subscribeUiEvents() {
        presenter.observeUiEvents()
            .subscribeWithErrorLogging {
                when (it) {
                    is AlbumPresenter.UiEvent.BackClick -> {
                        ribActivityInfoProvider.onBackPressed()
                    }
                }
            }.addToDisposables()
    }

}
