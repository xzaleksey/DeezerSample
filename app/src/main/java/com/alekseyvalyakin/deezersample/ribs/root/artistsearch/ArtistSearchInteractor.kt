package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import com.alekseyvalyakin.deezersample.common.repository.DeezerRepository
import com.alekseyvalyakin.deezersample.common.utils.rx.subscribeWithErrorLogging
import com.alekseyvalyakin.deezersample.di.schedulers.ThreadConfig
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.dependencies.ArtistChooseListener
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.rib.core.BaseInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [ArtistSearchScope].
 *
 */
@RibInteractor
class ArtistSearchInteractor : BaseInteractor<ArtistSearchPresenter, ArtistSearchRouter>() {

    @Inject
    lateinit var presenter: ArtistSearchPresenter
    @Inject
    lateinit var repo: DeezerRepository
    @Inject
    lateinit var artistChooseListener: ArtistChooseListener
    @field:[Inject ThreadConfig(ThreadConfig.TYPE.UI)]
    lateinit var uiScheduler: Scheduler
    private val queryRelay = BehaviorRelay.create<String>()
    private val modelRelay = BehaviorRelay.create<ArtistSearchModel>()

    //Should add retry policy, error state handle
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        initModel(savedInstanceState)
        subscribeModelUpdates()
        subscribePresenterEvents()
        subscribeSearchArtists()
    }

    private fun initModel(savedInstanceState: Bundle?) {
        val model = if (savedInstanceState != null) {
            val serializable = savedInstanceState.getSerializable(modelKey)
            serializable as ArtistSearchModel
        } else if (!modelRelay.hasValue()) {
            ArtistSearchModel()
        } else {
            null
        }
        if (model != null) {
            modelRelay.accept(model)
        }
        presenter.setSearchValue(modelRelay.value!!.query)
    }

    private fun subscribeModelUpdates() {
        modelRelay.observeOn(uiScheduler)
            .subscribeWithErrorLogging {
                presenter.showUi(it)
            }.addToDisposables()
    }

    private fun subscribePresenterEvents() {
        presenter.observeUiEvents()
            .flatMap {
                when (it) {
                    is ArtistSearchPresenter.UiEvent.SearchInput -> {
                        return@flatMap Observable.fromCallable {
                            queryRelay.accept(it.text)
                        }
                    }
                    is ArtistSearchPresenter.UiEvent.ChooseArtist -> {
                        return@flatMap Observable.fromCallable {
                            artistChooseListener.onArtistSelected(it.artistModel)
                        }
                    }
                }
            }
            .subscribeWithErrorLogging()
            .addToDisposables()
    }

    private fun subscribeSearchArtists() {
        queryRelay.switchMap(this::handleInput)
            .observeOn(uiScheduler)
            .subscribeWithErrorLogging { modelRelay.accept(it) }
            .addToDisposables()
    }

    private fun handleInput(text: String): Observable<ArtistSearchModel> {
        return when {
            text == modelRelay.value!!.query -> Observable.empty()
            text.isBlank() -> Observable.just(ArtistSearchModel(text, emptyList(), ArtistSearchState.SUCCESS))
            else -> {
                Timber.d("$text ${modelRelay.value!!.query}")
                repo.searchArtist(text).toObservable()
                    .map { ArtistSearchModel(text, it, ArtistSearchState.SUCCESS) }
                    .onErrorReturn { ArtistSearchModel(text, emptyList(), ArtistSearchState.FAIL) }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(modelKey, modelRelay.value)
    }
}
