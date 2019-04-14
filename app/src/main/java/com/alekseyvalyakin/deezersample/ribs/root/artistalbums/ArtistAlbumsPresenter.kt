package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.uber.rib.core.BasePresenter

/**
 * Presenter interface implemented by this RIB's view.
 */
interface ArtistAlbumsPresenter : BasePresenter<ArtistAlbumsPresenter.UiEvent, ArtistAlbumsModel> {

    sealed class UiEvent {
        object ClickBack : UiEvent()
        class ChooseAlbum(val albumModel: AlbumModel) : UiEvent()
    }
}