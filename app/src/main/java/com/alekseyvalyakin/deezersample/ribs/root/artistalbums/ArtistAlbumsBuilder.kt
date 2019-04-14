package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.di.rib.RibDependencyProvider
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.dependencies.AlbumChooseListener
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link ArtistAlbumsScope}.
 *
 */
class ArtistAlbumsBuilder(dependency: ParentComponent) :
    ViewBuilder<ArtistAlbumsView, ArtistAlbumsRouter, ArtistAlbumsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [ArtistAlbumsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [ArtistAlbumsRouter].
     */
    fun build(parentViewGroup: ViewGroup, artistModel: ArtistModel): ArtistAlbumsRouter {
        val view = createView(parentViewGroup)
        val interactor = ArtistAlbumsInteractor()
        val component = DaggerArtistAlbumsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .artistModel(artistModel)
            .build()
        return component.artistalbumsRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): ArtistAlbumsView? {
        return ArtistAlbumsView(parentViewGroup.context)
    }

    interface ParentComponent : RibDependencyProvider {
        fun getAlbumChooseListener(): AlbumChooseListener
    }

    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @ArtistAlbumsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: ArtistAlbumsView,
                interactor: ArtistAlbumsInteractor
            ): ArtistAlbumsRouter {
                return ArtistAlbumsRouter(view, interactor, component)
            }

            @ArtistAlbumsScope
            @Provides
            @JvmStatic
            internal fun presenter(
                artistAlbumsView: ArtistAlbumsView,
                artistModel: ArtistModel
            ): ArtistAlbumsPresenter {
                return ArtistAlbumsPresenterImpl(artistAlbumsView, artistModel)
            }
        }

    }

    @ArtistAlbumsScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<ArtistAlbumsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: ArtistAlbumsInteractor): Builder

            @BindsInstance
            fun view(view: ArtistAlbumsView): Builder

            @BindsInstance
            fun artistModel(artistModel: ArtistModel): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun artistalbumsRouter(): ArtistAlbumsRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class ArtistAlbumsScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class ArtistAlbumsInternal
}
