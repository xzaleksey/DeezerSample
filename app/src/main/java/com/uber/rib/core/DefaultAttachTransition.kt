package com.uber.rib.core

import android.view.ViewGroup

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
open class DefaultAttachTransition<R : ViewRouter<*, *, *>, S : RouterNavigatorState>(
        private val routerCreator: RouterCreator<R>,
        private val viewAttacher: ViewAttacher
) : RouterNavigator.AttachTransition<R, S> {

    override fun buildRouter(): R {
        return routerCreator.createRouter(viewAttacher.getViewGroup())
    }

    override fun willAttachToHost(router: R, previousState: S?, newState: S, isPush: Boolean) {
        viewAttacher.addView(router.view)
    }

    companion object {
        fun <R : ViewRouter<*, *, *>, S : RouterNavigatorState, B : BaseViewBuilder<*, R, *>> create(builder: B, parentView: ViewGroup): DefaultAttachTransition<R, S> {
            return DefaultAttachTransition(DefaultRouterCreator(builder), DefaultViewAttacher(parentView))
        }
    }
}