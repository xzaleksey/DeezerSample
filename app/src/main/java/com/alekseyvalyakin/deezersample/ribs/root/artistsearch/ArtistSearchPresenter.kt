package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.uber.rib.core.BasePresenter

/**
 * Presenter interface implemented by this RIB's view.
 */
interface ArtistSearchPresenter : BasePresenter<ArtistSearchPresenter.UiEvent, ArtistSearchModel> {
    fun setSearchValue(query: String)

    sealed class UiEvent {
        class SearchInput(val text: String) : UiEvent()
        class ChooseArtist(val artistModel: ArtistModel) : UiEvent()
    }


}