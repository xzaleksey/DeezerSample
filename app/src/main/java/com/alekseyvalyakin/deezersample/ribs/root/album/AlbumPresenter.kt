package com.alekseyvalyakin.deezersample.ribs.root.album

import com.uber.rib.core.BasePresenter

/**
 * Presenter interface implemented by this RIB's view.
 */
interface AlbumPresenter : BasePresenter<AlbumPresenter.UiEvent, AlbumViewModel> {

    sealed class UiEvent {
        object BackClick : UiEvent()
    }
}