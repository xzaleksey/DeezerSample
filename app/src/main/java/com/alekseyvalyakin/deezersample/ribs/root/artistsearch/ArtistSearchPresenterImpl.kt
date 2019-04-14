package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import com.alekseyvalyakin.deezersample.R
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.resources.ResourcesProviderImpl
import com.alekseyvalyakin.deezersample.common.utils.image.UrlRoundDrawableProviderImpl
import com.alekseyvalyakin.deezersample.common.view.getString
import com.alekseyvalyakin.deezersample.common.view.recyclerview.header.FlexibleHeaderModel
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.recycler.ArtistFlexibleViewModel
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.recycler.ArtistSearchFlexibleTypes
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.dimen
import java.util.concurrent.TimeUnit

class ArtistSearchPresenterImpl(
    private val artistSearchView: ArtistSearchView
) : ArtistSearchPresenter {

    private val resourceProvider = ResourcesProviderImpl(artistSearchView.context)
    private val size = artistSearchView.context.dimen(R.dimen.unit_7)
    private val qualityChooser = ArtistModel.getQualityChooser(size)

    override fun observeUiEvents(): Observable<ArtistSearchPresenter.UiEvent> {
        return Observable.merge(
            listOf(
                observeSearchInput(),
                observeItemClicks()
            )
        )
    }

    fun observeItemClicks(): Observable<ArtistSearchPresenter.UiEvent.ChooseArtist> {
        return artistSearchView.observeItemClicks()
            .filter { it.layoutRes == ArtistSearchFlexibleTypes.ARTIST }
            .cast(ArtistFlexibleViewModel::class.java)
            .map { ArtistSearchPresenter.UiEvent.ChooseArtist(it.artistModel) }
    }

    override fun showUi(viewModel: ArtistSearchModel) {
        val items = mutableListOf<IFlexible<*>>()
        for (artistModel in viewModel.artistResponse) {
            items.add(
                ArtistFlexibleViewModel(
                    UrlRoundDrawableProviderImpl(
                        qualityChooser(artistModel)!!,
                        resourceProvider
                    ), artistModel
                )
            )
        }
        if (items.isNotEmpty()) {
            items.add(0, FlexibleHeaderModel(artistSearchView.getString(R.string.artists)))
        }
        artistSearchView.showData(items)

        if (viewModel.state == ArtistSearchState.FAIL) {
            artistSearchView.showError(artistSearchView.getString(R.string.error))
        }
    }

    override fun setSearchValue(query: String) {
        artistSearchView.setSearchValue(query)
    }

    private fun observeSearchInput(): Observable<ArtistSearchPresenter.UiEvent.SearchInput> =
        artistSearchView.observeInput()
            .debounce(200L, TimeUnit.MILLISECONDS, Schedulers.computation())
            .throttleLast(100L, TimeUnit.MILLISECONDS, Schedulers.computation())
            .map { ArtistSearchPresenter.UiEvent.SearchInput(it.toString()) }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
}