package com.alekseyvalyakin.deezersample.ribs.root

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.di.rib.RibDependencyProvider
import com.alekseyvalyakin.deezersample.ribs.root.album.AlbumBuilder
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.ArtistAlbumsBuilder
import com.alekseyvalyakin.deezersample.ribs.root.artistalbums.dependencies.AlbumChooseListener
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.ArtistSearchBuilder
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.dependencies.ArtistChooseListener
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class RootBuilder(dependency: ParentComponent) :
    ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(parentViewGroup: ViewGroup): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor()
        val component = DaggerRootBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.rootRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView? {
        return RootView(parentViewGroup.context)
    }

    interface ParentComponent : RibDependencyProvider

    @dagger.Module
    abstract class Module {

        @RootScope
        @Binds
        abstract fun presenter(view: RootView): RootInteractor.RootPresenter

        @RootScope
        @Binds
        abstract fun artistChooseListener(interactor: RootInteractor): ArtistChooseListener

        @RootScope
        @Binds
        abstract fun albumChooseListener(interactor: RootInteractor): AlbumChooseListener

        @dagger.Module
        companion object {

            @RootScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: RootView,
                interactor: RootInteractor
            ): RootRouter {
                return RootRouter(
                    view,
                    interactor,
                    component,
                    ArtistSearchBuilder(component),
                    ArtistAlbumsBuilder(component),
                    AlbumBuilder(component)
                )
            }
        }

    }

    @RootScope
    @dagger.Component(modules = [Module::class], dependencies = [ParentComponent::class])
    interface Component : InteractorBaseComponent<RootInteractor>, BuilderComponent,
        ArtistSearchBuilder.ParentComponent, ArtistAlbumsBuilder.ParentComponent, AlbumBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RootInteractor): Builder

            @BindsInstance
            fun view(view: RootView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class RootScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class RootInternal
}
