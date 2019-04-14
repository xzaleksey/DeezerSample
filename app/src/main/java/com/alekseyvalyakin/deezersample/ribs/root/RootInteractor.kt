package com.alekseyvalyakin.deezersample.ribs.root

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.dependencies.AlbumChooseListener
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.dependencies.ArtistChooseListener
import com.uber.rib.core.BaseInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 *
 */
@RibInteractor
class RootInteractor : BaseInteractor<RootInteractor.RootPresenter, RootRouter>(),
    ArtistChooseListener,
    AlbumChooseListener {

    @Inject
    lateinit var presenter: RootPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        if (savedInstanceState == null) {
            router.attachArtistSearch()
        }
    }

    override fun onArtistSelected(artistModel: ArtistModel) {
        router.attachAlbums(artistModel)
    }

    override fun onAlbumSelected(albumModel: AlbumModel) {
        router.attachAlbum(albumModel.id)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter
}
