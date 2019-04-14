package com.alekseyvalyakin.deezersample.ribs.root

import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.ribs.root.RootRouter.State.Companion.ALBUMS_NAME
import com.alekseyvalyakin.deezersample.ribs.root.RootRouter.State.Companion.ALBUM_NAME
import com.alekseyvalyakin.deezersample.ribs.root.album.AlbumBuilder
import com.alekseyvalyakin.deezersample.ribs.root.album.AlbumRouter
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.ArtistAlbumsBuilder
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.ArtistAlbumsRouter
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.ArtistSearchBuilder
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.ArtistSearchRouter
import com.uber.rib.core.*
import java.io.Serializable

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 */
class RootRouter(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    artistSearchBuilder: ArtistSearchBuilder,
    val albumsBuilder: ArtistAlbumsBuilder,
    val albumBuilder: AlbumBuilder
) : BaseRouter<RootView, RootInteractor, RootBuilder.Component, RootRouter.State>(view, interactor, component) {

    private val artistSearchAttachTransition =
        DefaultAttachTransition.create<ArtistSearchRouter, State, ArtistSearchBuilder>(artistSearchBuilder, view)
    private val artistSearchDetachTransition = DefaultDetachTransition<ArtistSearchRouter, State>(view)

    private val albumsArtistDetachTransition = DefaultDetachTransition<ArtistAlbumsRouter, State>(view)
    private val albumDetachTransition = DefaultDetachTransition<AlbumRouter, State>(view)

    override fun initNavigator(navigator: MutableMap<String, (AttachInfo<State>) -> Boolean>) {
        navigator.registerStateHandler(State.ArtistSearch(), artistSearchAttachTransition, artistSearchDetachTransition)
        initArtistAlbums(navigator)
        initAlbum(navigator)
    }

    private fun initArtistAlbums(navigator: MutableMap<String, (AttachInfo<State>) -> Boolean>) {
        navigator[ALBUMS_NAME] = {
            val state = it.state as State.Albums
            val attachTransition = getAlbumsAttachTransition(state.artistModel)

            if (it.isTransient) {
                internalPushTransientState(state, attachTransition, albumsArtistDetachTransition)
            } else {
                internalPushRetainedState(state, attachTransition, albumsArtistDetachTransition)
            }

        }
    }

    private fun initAlbum(navigator: MutableMap<String, (AttachInfo<State>) -> Boolean>) {
        navigator[ALBUM_NAME] = {
            val state = it.state as State.Album
            val attachTransition = getAlbumAttachTransition(state.albumId)

            if (it.isTransient) {
                internalPushTransientState(state, attachTransition, albumDetachTransition)
            } else {
                internalPushRetainedState(state, attachTransition, albumDetachTransition)
            }
        }
    }

    //could be moved to separate classes, animations could be added
    private fun getAlbumsAttachTransition(artistModel: ArtistModel): DefaultAttachTransition<ArtistAlbumsRouter, State> {
        return DefaultAttachTransition(
            object : RouterCreator<ArtistAlbumsRouter> {
                override fun createRouter(view: ViewGroup): ArtistAlbumsRouter {
                    return albumsBuilder.build(view, artistModel)
                }
            },
            DefaultViewAttacher(view)
        )
    }

    //could be moved to separate classes, animations could be added
    private fun getAlbumAttachTransition(albumId: String): DefaultAttachTransition<AlbumRouter, State> {
        return DefaultAttachTransition(
            object : RouterCreator<AlbumRouter> {
                override fun createRouter(view: ViewGroup): AlbumRouter {
                    return albumBuilder.build(view, albumId)
                }
            },
            DefaultViewAttacher(view)
        )
    }

    fun attachArtistSearch() {
        attachRibForState(State.ArtistSearch(), false)
    }

    fun attachAlbums(artistModel: ArtistModel) {
        attachRibForState(State.Albums(artistModel), false)
    }

    fun attachAlbum(albumId: String) {
        attachRibForState(State.Album(albumId), false)
    }

    override fun hasOwnContent(): Boolean {
        return false
    }

    sealed class State(val name: String) : SerializableRouterNavigatorState {
        override fun name(): String {
            return name
        }

        class ArtistSearch : State("ArtistSearch")

        class Albums(val artistModel: ArtistModel) : State(ALBUMS_NAME) {
            override fun getRestorableInfo(): Serializable? {
                return artistModel
            }
        }

        class Album(val albumId: String) : State(ALBUM_NAME) {
            override fun getRestorableInfo(): Serializable? {
                return albumId
            }
        }

        companion object {
            const val ALBUMS_NAME = "Albums"
            const val ALBUM_NAME = "Album"
        }
    }
}
