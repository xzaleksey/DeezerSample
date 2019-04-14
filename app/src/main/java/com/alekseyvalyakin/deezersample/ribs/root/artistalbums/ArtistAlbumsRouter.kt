package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link ArtistAlbumsBuilder.ArtistAlbumsScope}.
 *
 */
class ArtistAlbumsRouter(
    view: ArtistAlbumsView,
    interactor: ArtistAlbumsInteractor,
    component: ArtistAlbumsBuilder.Component) : ViewRouter<ArtistAlbumsView, ArtistAlbumsInteractor, ArtistAlbumsBuilder.Component>(view, interactor, component)
