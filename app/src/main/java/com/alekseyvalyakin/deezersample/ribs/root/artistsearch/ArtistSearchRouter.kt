package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link ArtistSearchBuilder.ArtistSearchScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class ArtistSearchRouter(
    view: ArtistSearchView,
    interactor: ArtistSearchInteractor,
    component: ArtistSearchBuilder.Component) : ViewRouter<ArtistSearchView, ArtistSearchInteractor, ArtistSearchBuilder.Component>(view, interactor, component)
