package com.uber.rib.core

import io.reactivex.Observable

interface BasePresenter<E, V> {
    fun observeUiEvents(): Observable<E>

    fun showUi(viewModel: V)
}