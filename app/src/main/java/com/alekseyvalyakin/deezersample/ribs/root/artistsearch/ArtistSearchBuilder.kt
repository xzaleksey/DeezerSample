package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alekseyvalyakin.deezersample.di.rib.RibDependencyProvider
import com.alekseyvalyakin.deezersample.ribs.root.artistsearch.dependencies.ArtistChooseListener
import com.uber.rib.core.BaseViewBuilder
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link ArtistSearchScope}.
 *
 */
class ArtistSearchBuilder(dependency: ParentComponent) :
    BaseViewBuilder<ArtistSearchView, ArtistSearchRouter, ArtistSearchBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [ArtistSearchRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [ArtistSearchRouter].
     */
    override fun build(parentViewGroup: ViewGroup): ArtistSearchRouter {
        val view = createView(parentViewGroup)
        val interactor = ArtistSearchInteractor()
        val component = DaggerArtistSearchBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.artistsearchRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): ArtistSearchView {
        return ArtistSearchView(parentViewGroup.context)
    }

    interface ParentComponent : RibDependencyProvider {
        fun getArtistChooseListener(): ArtistChooseListener
    }

    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @ArtistSearchScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: ArtistSearchView,
                interactor: ArtistSearchInteractor
            ): ArtistSearchRouter {
                return ArtistSearchRouter(view, interactor, component)
            }

            @ArtistSearchScope
            @Provides
            @JvmStatic
            fun presenter(view: ArtistSearchView): ArtistSearchPresenter {
                return ArtistSearchPresenterImpl(view)
            }

        }

    }

    @ArtistSearchScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<ArtistSearchInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: ArtistSearchInteractor): Builder

            @BindsInstance
            fun view(view: ArtistSearchView): Builder

            fun parentComponent(component: ParentComponent): Builder

            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun artistsearchRouter(): ArtistSearchRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class ArtistSearchScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class ArtistSearchInternal
}
