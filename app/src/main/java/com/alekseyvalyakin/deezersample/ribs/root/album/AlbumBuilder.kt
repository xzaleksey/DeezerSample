package com.alekseyvalyakin.deezersample.ribs.root.album

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.di.rib.RibDependencyProvider
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link AlbumScope}.
 *
 */
class AlbumBuilder(dependency: ParentComponent) :
    ViewBuilder<AlbumView, AlbumRouter, AlbumBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [AlbumRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [AlbumRouter].
     */
    fun build(
        parentViewGroup: ViewGroup,
        albumId: String
    ): AlbumRouter {
        val view = createView(parentViewGroup)
        val interactor = AlbumInteractor()
        val component = DaggerAlbumBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .albumId(albumId)
            .build()
        return component.albumRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): AlbumView {
        return AlbumView(parentViewGroup.context)
    }

    interface ParentComponent : RibDependencyProvider

    @dagger.Module
    abstract class Module {


        @dagger.Module
        companion object {

            @AlbumScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: AlbumView,
                interactor: AlbumInteractor
            ): AlbumRouter {
                return AlbumRouter(view, interactor, component)
            }


            @AlbumScope
            @Provides
            @JvmStatic
            internal fun presenter(
                view: AlbumView
            ): AlbumPresenter {
                return AlbumPresenterImpl(view)
            }
        }

    }

    @AlbumScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<AlbumInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: AlbumInteractor): Builder

            @BindsInstance
            fun view(view: AlbumView): Builder

            @BindsInstance
            fun albumId(albumId: String): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun albumRouter(): AlbumRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class AlbumScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class AlbumInternal
}
