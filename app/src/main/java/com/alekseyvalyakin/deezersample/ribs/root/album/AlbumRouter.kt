package com.alekseyvalyakin.deezersample.ribs.root.album

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link AlbumBuilder.AlbumScope}.
 *
 */
class AlbumRouter(
    view: AlbumView,
    interactor: AlbumInteractor,
    component: AlbumBuilder.Component) : ViewRouter<AlbumView, AlbumInteractor, AlbumBuilder.Component>(view, interactor, component)
