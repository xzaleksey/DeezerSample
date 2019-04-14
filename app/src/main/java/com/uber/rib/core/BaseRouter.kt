package com.uber.rib.core

import android.annotation.SuppressLint
import android.support.annotation.IntRange
import android.view.View
import timber.log.Timber
import java.io.Serializable
import java.util.*

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA", "LeakingThis")
open class BaseRouter<V : View, I : Interactor<*, out Router<I, C>>,
        C : InteractorBaseComponent<I>,
        StateT : SerializableRouterNavigatorState>(view: V, interactor: I, component: C
) : ViewRouter<V, I, C>(view, interactor, component), RouterNavigator<StateT> {
    private val myTag = "MyModernRouter"
    private val tempBundle = Bundle()
    private val navigationStack = ArrayDeque<RouterNavigator.RouterAndState<StateT>>()
    private val hostRouterName: String = javaClass.simpleName
    private var currentTransientRouterAndState: RouterNavigator.RouterAndState<StateT>? = null
    private val navigator = mutableMapOf<String, (AttachInfo<StateT>) -> Boolean>()

    @SuppressLint("VisibleForTests")
    override fun saveInstanceState(outState: Bundle) {
        val routersToSave: Set<Router<*, *>> = getRoutersToSave()
        super.saveInstanceState(outState)
        val bundle = outState.getBundleExtra(KEY_CHILD_ROUTERS)!!
        for (router in routersToSave) {
            val routerTag = getRouterTag(router)
            Timber.d("saved $routerTag")
            bundle.putBundleExtra(routerTag, tempBundle.getBundleExtra(routerTag))
        }
        outState.putSerializable(myTag, SavedState(
                navigationStack.toList().map { AttachInfo<StateT>(it.state, false) },
                currentTransientRouterAndState?.state?.run { AttachInfo(this, true) }
        ))
    }

    override fun dispatchAttach(savedInstanceState: Bundle?, tag: String?) {
        initNavigator(navigator)
        super.dispatchAttach(savedInstanceState, tag)
        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        }

        if (savedInstanceState == null) {
            currentTransientRouterAndState?.router?.run {
                attachChildRouter(this, savedInstanceState)
            } ?: navigationStack.peek()?.router?.run {
                attachChildRouter(this, savedInstanceState)
            }
        }
    }

    private fun attachChildRouter(router: Router<*, *>, savedInstanceState: Bundle?) {
        if (!router.interactor.isAttached) {
            router.dispatchAttach(savedInstanceState, getRouterTag(router))
        }
    }

    public override fun detachChild(childRouter: Router<out Interactor<*, *>, out InteractorBaseComponent<*>>) {
        if (navigationStack.indexOfFirst { it.router == childRouter } >= 0) {
            val bundle = Bundle()
            childRouter.saveInstanceState(bundle)
            val routerTag = getRouterTag(childRouter)
            Timber.d("saved  $routerTag")
            tempBundle.putBundleExtra(routerTag, bundle)
        }
        super.detachChild(childRouter)
    }

    open fun attachRibForState(state: StateT, isTransient: Boolean = true): Boolean {
        return navigateToRib(AttachInfo(state, isTransient))
    }

    open fun attachRib(attachInfo: AttachInfo<StateT>): Boolean {
        return navigateToRib(attachInfo)
    }

    open fun initNavigator(navigator: MutableMap<String, (AttachInfo<StateT>) -> Boolean>) {

    }

    private fun navigateToRib(attachInfo: AttachInfo<StateT>): Boolean {
        return navigator[attachInfo.state.name()]?.invoke(attachInfo) ?: fallbackNavigateToRib(attachInfo)
    }

    protected open fun fallbackNavigateToRib(attachInfo: AttachInfo<StateT>): Boolean {
        return false
    }

    init {
        log(String.format(
                Locale.getDefault(),
                "Installed new RouterNavigator: Hosting Router -> %s",
                hostRouterName)
        )
    }

    override fun popState() {
        // If we are in a transient stateArtist, go ahead and pop that stateArtist.
        var fromState: RouterNavigator.RouterAndState<StateT>? = null
        if (currentTransientRouterAndState != null) {
            fromState = currentTransientRouterAndState
            val fromRouterName = fromState!!.router.javaClass.simpleName
            currentTransientRouterAndState = null
            log(String.format(
                    Locale.getDefault(),
                    "Preparing to pop existing transient stateArtist for router: %s",
                    fromRouterName))
        } else {
            if (!navigationStack.isEmpty()) {
                fromState = navigationStack.pop()
                val fromRouterName = fromState!!.router.javaClass.simpleName
                log(String.format(
                        Locale.getDefault(),
                        "Preparing to pop existing stateArtist for router: %s",
                        fromRouterName))
            }
        }

        if (fromState != null) {
            // Pull the incoming stateArtist (So we can restore it.)
            var toState: RouterNavigator.RouterAndState<StateT>? = null
            if (!navigationStack.isEmpty()) {
                toState = navigationStack.peek()
            }

            detachInternal(fromState, toState, false)

            if (toState != null) {
                attachInternal(fromState, toState, false)
            }
        } else {
            log("No stateArtist to pop. No action will be taken.")
        }
    }

    override fun <R : Router<*, *>> pushRetainedState(
            newState: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>?): R? {
        return pushInternal(newState, attachTransition, detachTransition, false)
    }

    fun <R : Router<*, *>> internalPushRetainedState(
            newState: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>?
    ): Boolean {
        return pushInternal(newState, attachTransition, detachTransition, false) != null
    }

    override fun <R : Router<*, *>> pushRetainedState(
            newState: StateT, attachTransition: RouterNavigator.AttachTransition<R, StateT>): R? {
        return pushRetainedState(newState, attachTransition, null)
    }

    fun <R : Router<*, *>> internalPushTransientState(
            newState: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>?
    ): Boolean {
        return pushInternal(newState, attachTransition, detachTransition, true) != null
    }

    override fun <R : Router<*, *>> pushTransientState(
            newState: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>?): R? {
        return pushInternal(newState, attachTransition, detachTransition, true)
    }

    override fun <R : Router<*, *>> pushTransientState(
            newState: StateT, attachTransition: RouterNavigator.AttachTransition<R, StateT>): R? {
        return pushTransientState(newState, attachTransition, null)
    }

    override fun peekRouter(): Router<*, *>? {
        val top = peekCurrentRouterAndState() ?: return null
        return top.router
    }

    override fun peekState(): StateT? {
        val top = peekCurrentRouterAndState() ?: return null
        return top.state
    }

    @IntRange(from = 0)
    override fun size(): Int {
        val stackSize = if (currentTransientRouterAndState == null) 0 else 1
        return navigationStack.size + stackSize
    }

    override fun hostWillDetach() {
        log(String.format(
                Locale.getDefault(), "Detaching RouterNavigator from host -> %s", hostRouterName))
        val currentRouterAndState = peekCurrentRouterAndState()
        detachInternal(currentRouterAndState, null as StateT?, false)
        currentTransientRouterAndState = null
        navigationStack.clear()
    }

    /**
     * Handles the detachment of a router.
     *
     * @param fromRouterState Previous stateArtist
     * @param toRouterState New stateArtist
     * @param isPush True if this is caused by a push
     */
    private fun detachInternal(
            fromRouterState: RouterNavigator.RouterAndState<StateT>?,
            toRouterState: RouterNavigator.RouterAndState<StateT>?,
            isPush: Boolean) {
        detachInternal(fromRouterState, toRouterState?.state, isPush)
    }

    /**
     * Handles the detachment of a router.
     *
     * @param fromRouterState Previous stateArtist
     * @param toState New stateArtist
     * @param isPush True if this is caused by a push
     */
    private fun detachInternal(
            fromRouterState: RouterNavigator.RouterAndState<StateT>?,
            toState: StateT?,
            isPush: Boolean) {
        if (fromRouterState == null) {
            log("No router to transition from. Call to detach will be dropped.")
            return
        }

        val callback = fromRouterState.detachCallback
        val fromRouterName = fromRouterState.router.javaClass.simpleName

        if (callback != null) {
            log(String.format(Locale.getDefault(), "Calling willDetachFromHost for %s", fromRouterName))
            callback.willDetachFromHost(
                    fromRouterState.router, fromRouterState.state, toState, isPush)
        }
        log(String.format(Locale.getDefault(), "Detaching %s from %s", fromRouterName, hostRouterName))
        detachChild(fromRouterState.router)
        if (callback != null) {
            log(
                    String.format(
                            Locale.getDefault(), "Calling onPostDetachFromHost for %s", fromRouterName))
            callback.onPostDetachFromHost(fromRouterState.router, toState, isPush)
        }
    }

    /**
     * Handles the attachment logic for a router.
     *
     * @param fromRouterState From router stateArtist.
     * @param toRouterState New stateArtist.
     * @param isPush True if this is from a push.
     */
    private fun attachInternal(
            fromRouterState: RouterNavigator.RouterAndState<StateT>?,
            toRouterState: RouterNavigator.RouterAndState<StateT>,
            isPush: Boolean) {
        val toRouterName = toRouterState.router.javaClass.simpleName
        val attachCallback = toRouterState.attachTransition

        log(String.format(Locale.getDefault(), "Calling willAttachToHost for %s", toRouterName))
        attachCallback.willAttachToHost(
                toRouterState.router,
                fromRouterState?.state,
                toRouterState.state,
                isPush)
        log(String.format(
                Locale.getDefault(), "Attaching %s as a child of %s", toRouterName, hostRouterName))
        val routerTag = getRouterTag(toRouterState.router)
        Timber.d("attached $routerTag")
        attachChild(toRouterState.router, routerTag)
    }

    private fun getRouterTag(router: Router<*, *>): String {
        val index = navigationStack.indexOfFirst { it.router == router }
        if (index >= 0) {
            return router::class.java.name + (navigationStack.size - 1 - index)
        }
        return router::class.java.name
    }

    private fun peekCurrentState(): StateT? {
        val currentRouterAndState = peekCurrentRouterAndState()
        return currentRouterAndState?.state
    }

    /**
     * Handles the pushing logic.
     *
     * @param newState New stateArtist
     * @param attachTransition Transition to use during attach.
     * @param detachTransition Detach transition to use during pop.
     * @param isTransient True if this is a transient entry.
     * @param <R> Router type.
    </R> */
    private fun <R : Router<*, *>> pushInternal(
            newState: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>?,
            isTransient: Boolean
    ): R? {
        val fromRouterAndState = peekCurrentRouterAndState()
        val fromState = peekCurrentState()
        if (fromState == null || fromState.name() != newState.name()) {
            if (fromRouterAndState != null) {
                detachInternal(fromRouterAndState, newState, true)
            }
            currentTransientRouterAndState = null

            val newRouter = attachTransition.buildRouter()
            log(String.format(Locale.getDefault(), "Built new router - %s", newRouter))
            attachTransition.willAttachToHost(newRouter, fromState, newState, true)
            val newRouterName = newRouter.javaClass.simpleName
            log(String.format(Locale.getDefault(), "Calling willAttachToHost for %s", newRouterName))

            val routerAndState = RouterNavigator.RouterAndState(newRouter, newState, attachTransition, detachTransition)

            if (isTransient) {
                currentTransientRouterAndState = routerAndState
            } else {
                navigationStack.push(routerAndState)
            }
            log(String.format(
                    Locale.getDefault(), "Attaching %s as a child of %s", newRouterName, hostRouterName))
            val routerTag = getRouterTag(newRouter)
            Timber.d("attached $routerTag")
            attachChild(newRouter, routerTag)
            return newRouter
        }
        return null
    }

    private fun getRoutersToSave(): Set<Router<*, *>> {
        val result = mutableSetOf<Router<*, *>>()

        for (routerAndState in navigationStack) {
            if (!children.contains(routerAndState.router)) {
                result.add(routerAndState.router)
            }
        }

        return result
    }

    @Suppress("UNCHECKED_CAST", "BinaryOperationInTimber")
    private fun restoreState(bundle: Bundle) {
        Timber.d("restore stateArtist")
        val savedState: SavedState<StateT> = bundle.getSerializable(myTag) as SavedState<StateT>
        for (attachInfo in savedState.stack.reversed()) {
            attachRib(attachInfo)
        }
        savedState.currentTransientState?.run {
            attachRib(this)
        }
    }

    private fun peekCurrentRouterAndState(): RouterNavigator.RouterAndState<StateT>? {
        return if (currentTransientRouterAndState != null) {
            currentTransientRouterAndState
        } else if (!navigationStack.isEmpty()) {
            navigationStack.peek()
        } else {
            null
        }
    }

    fun isEmptyStack() = navigationStack.isEmpty()

    override fun handleBackPress(): Boolean {
        val handleBackPress = this.interactor.handleBackPress()

        if (handleBackPress) {
            return true
        }

        if (onBackPressed()) {
            return true
        }

        return handleBackPress
    }

    open fun onBackPressed(): Boolean {
        val currentRouter = peekRouter()
        if (currentRouter != null && currentRouter.handleBackPress()) {
            return true
        }

        val isPoppedChild = peekState() != null
        popState()
        if (isPoppedChild) {
            if (peekState() != null || hasOwnContent()) {
                return true
            }
        }

        return false
    }

    open fun hasOwnContent(): Boolean {
        return true
    }

    fun closeAllChildren() {
        while (peekCurrentState() != null) {
            popState()
        }
    }

    fun closeAllExceptFirst() {
        while (navigationStack.size > 1) {
            navigationStack.removeLast()
        }
    }

    fun closeChild(name: String): Boolean {
        if (peekCurrentState()?.name() == name) {
            popState()
            return true
        }

        return navigationStack.removeIfFiltered { it.state.name() == name }
    }

    /** Writes out to the debug log.  */
    private fun log(text: String) {
        Rib.getConfiguration().handleDebugMessage("%s: $text", "RouterNavigator")
    }

    data class SavedState<StateT : SerializableRouterNavigatorState>(
            val stack: List<AttachInfo<StateT>>,
            val currentTransientState: AttachInfo<StateT>? = null
    ) : Serializable

    fun <R : Router<*, *>> MutableMap<String, (AttachInfo<StateT>) -> Boolean>.registerStateHandler(
            state: StateT,
            attachTransition: RouterNavigator.AttachTransition<R, StateT>,
            detachTransition: RouterNavigator.DetachTransition<R, StateT>
    ) {
        this[state.name()] = { attachInfo ->
            if (attachInfo.isTransient) {
                internalPushTransientState(attachInfo.state, attachTransition, detachTransition)
            } else {
                internalPushRetainedState(attachInfo.state, attachTransition, detachTransition)
            }
        }
    }

    fun <E> MutableCollection<E>.removeIfFiltered(filter: (element: E) -> Boolean): Boolean {
        var removed = false
        val each = iterator()
        while (each.hasNext()) {
            if (filter(each.next())) {
                each.remove()
                removed = true
            }
        }
        return removed
    }
}
