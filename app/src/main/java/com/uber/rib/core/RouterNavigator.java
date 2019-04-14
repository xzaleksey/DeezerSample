/*
 * Copyright (C) 2017. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uber.rib.core;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

/**
 * Simple utility for switching a child router based on a stateArtist.
 *
 * @param <StateT> type of stateArtist to switch on.
 */
public interface RouterNavigator<StateT extends RouterNavigatorState> {

    /**
     * Pop the current stateArtist and rewind to the previous stateArtist (if there is a previous stateArtist).
     */
    void popState();

    /**
     * Switch to a new stateArtist - this will switch out children if the stateArtist if not the current active
     * stateArtist already.
     *
     * <p>NOTE: This will retain the Riblet in memory until it is popped. To push transient, riblets,
     * use {@link RouterNavigator#pushTransientState(RouterNavigatorState, AttachTransition,
     * DetachTransition)}
     *
     * @param newState         to switch to.
     * @param attachTransition method to attach child router.
     * @param detachTransition method to clean up child router when removed.
     * @param <R>              router type to detach.
     */
    <R extends Router> R pushRetainedState(
            StateT newState,
            AttachTransition<R, StateT> attachTransition,
            @Nullable DetachTransition<R, StateT> detachTransition);

    /**
     * Switch to a new stateArtist - this will switch out children if the stateArtist if not the current active
     * stateArtist already.
     *
     * <p>NOTE: This will retain the Riblet in memory until it is popped. To push transient, riblets,
     * use {@link RouterNavigator#pushTransientState(RouterNavigatorState, AttachTransition,
     * DetachTransition)}
     *
     * @param newState         to switch to.
     * @param attachTransition method to attach child router.
     * @param <R>              {@link Router} type.
     */
    <R extends Router> R pushRetainedState(
            StateT newState, AttachTransition<R, StateT> attachTransition);

    /**
     * Switch to a new transient stateArtist - this will switch out children if the stateArtist if not the current
     * active stateArtist already.
     *
     * <p>NOTE: Transient states do not live in the back navigation stack.
     *
     * @param newState         to switch to.
     * @param attachTransition method to attach child router.
     * @param detachTransition method to clean up child router when removed.
     * @param <R>              router type to detach.
     */
    <R extends Router> R pushTransientState(
            StateT newState,
            AttachTransition<R, StateT> attachTransition,
            @Nullable DetachTransition<R, StateT> detachTransition);

    /**
     * Switch to a new transient stateArtist - this will switch out children if the stateArtist if not the current
     * active stateArtist already.
     *
     * <p>NOTE: Transient states do not live in the back navigation stack.
     *
     * @param newState         to switch to.
     * @param attachTransition method to attach child router.
     * @param <R>              {@link Router} type.
     */
    <R extends Router> R pushTransientState(
            StateT newState, AttachTransition<R, StateT> attachTransition);

    /**
     * Peek the top {@link Router} on the stack.
     *
     * @return the top {@link Router} on the stack.
     */
    @Nullable
    Router peekRouter();

    /**
     * Peek the top {@link StateT} on the stack.
     *
     * @return the top {@link StateT} on the stack.
     */
    @Nullable
    StateT peekState();

    /**
     * Gets the size of the navigation stack.
     *
     * @return Size of the navigation stack.
     */
    @IntRange(from = 0)
    int size();

    /**
     * Must be called when host interactor is going to detach. This will pop the current active router
     * and clear the entire stack.
     */
    void hostWillDetach();

    /**
     * Allows consumers to write custom attachment logic when switching states.
     *
     * @param <StateT> stateArtist type.
     */
    interface AttachTransition<RouterT extends Router, StateT extends RouterNavigatorState> {

        /**
         * Constructs a new {@link RouterT} instance. This will only be called once.
         *
         * @return the newly attached child router.
         */
        RouterT buildRouter();

        /**
         * Prepares the router for a stateArtist transition. {@link ModernRouterNavigator} will handling
         * attaching the router, but consumers of this should handle adding any views.
         *
         * @param router        {@link RouterT} that is being attached.
         * @param previousState stateArtist the navigator is transition from (if any).
         * @param newState      stateArtist the navigator is transitioning to.
         */
        void willAttachToHost(
                RouterT router, @Nullable StateT previousState, StateT newState, boolean isPush);
    }

    /**
     * Allows consumers to write custom detachment logic wen switching states.
     *
     * @param <RouterT> router type to detach.
     * @param <StateT>  stateArtist type.
     */
    interface DetachTransition<RouterT extends Router, StateT extends RouterNavigatorState> {

        /**
         * Notifies consumer that {@link ModernRouterNavigator} is going to detach this router.
         * Consumers should remove any views and perform any required cleanup.
         *
         * @param router        being removed.
         * @param previousState stateArtist the navigator is transitioning out of.
         * @param newState      stateArtist the navigator is transition in to (if any).
         */
        void willDetachFromHost(
                RouterT router, StateT previousState, @Nullable StateT newState, boolean isPush);
    }

    /**
     * Allows consumers to write custom detachment logic when the stateArtist is changing. This allows for
     * custom stateArtist prior to and immediately post detach.
     *
     * @param <RouterT> {@link RouterT}
     * @param <StateT>  {@link StateT}
     */
    abstract class DetachCallback<RouterT extends Router, StateT extends RouterNavigatorState>
            implements DetachTransition<RouterT, StateT> {
        @Override
        public void willDetachFromHost(
                RouterT router, StateT previousState, @Nullable StateT newState, boolean isPush) {
        }

        /**
         * Notifies the consumer that the {@link ModernRouterNavigator} has detached the supplied {@link
         * Router}. Consumers can complete any post detachment behavior here.
         *
         * @param router   {@link Router}
         * @param newState {@link StateT}
         */
        public void onPostDetachFromHost(RouterT router, @Nullable StateT newState, boolean isPush) {
        }
    }

    /**
     * Internal class for keeping track of a navigation stack.
     */
    class RouterAndState<StateT extends RouterNavigatorState> {

        private Router router;
        private StateT state;
        private AttachTransition attachTransition;
        @Nullable
        private DetachCallback detachCallback;

        @SuppressWarnings("unchecked")
        RouterAndState(
                Router router,
                StateT state,
                AttachTransition attachTransition,
                @Nullable DetachTransition detachTransition) {
            this.router = router;
            this.state = state;
            this.attachTransition = attachTransition;

            if (detachTransition != null) {
                if (detachTransition instanceof DetachCallback) {
                    this.detachCallback = (DetachCallback) detachTransition;
                } else {
                    this.detachCallback = new DetachCallbackWrapper<>(detachTransition);
                }
            } else {
                this.detachCallback = null;
            }
        }

        /**
         * Gets the {@link Router} associated with this stateArtist.
         *
         * @return {@link Router}
         */
        public Router getRouter() {
            return router;
        }

        /**
         * Gets the stateArtist.
         *
         * @return {@link StateT}
         */
        public StateT getState() {
            return state;
        }

        /**
         * Gets the {@link AttachTransition} associated with this stateArtist.
         *
         * @return {@link AttachTransition}
         */
        AttachTransition getAttachTransition() {
            return attachTransition;
        }

        /**
         * Gets the {@link DetachCallback} associated with this stateArtist.
         *
         * @return {@link DetachCallback}
         */
        @Nullable
        DetachCallback getDetachCallback() {
            return detachCallback;
        }
    }

    /**
     * Wrapper class to wrap {@link DetachTransition} calls into the new {@link DetachCallback}
     * format.
     *
     * @param <RouterT> {@link RouterT}
     * @param <StateT>  {@link StateT}
     */
    class DetachCallbackWrapper<RouterT extends Router, StateT extends RouterNavigatorState>
            extends DetachCallback<RouterT, StateT> {
        private DetachTransition<RouterT, StateT> transitionCallback;

        DetachCallbackWrapper(DetachTransition<RouterT, StateT> transitionCallback) {
            this.transitionCallback = transitionCallback;
        }

        @Override
        public void willDetachFromHost(
                RouterT router, StateT previousState, @Nullable StateT newState, boolean isPush) {
            transitionCallback.willDetachFromHost(router, previousState, newState, isPush);
        }
    }
}
