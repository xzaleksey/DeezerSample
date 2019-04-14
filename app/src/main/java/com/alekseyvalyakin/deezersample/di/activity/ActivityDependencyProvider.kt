package com.alekseyvalyakin.deezersample.di.activity

import com.alekseyvalyakin.deezersample.common.ribs.RibActivityInfoProvider

interface ActivityDependencyProvider {
    fun getRibActivityInfoProvider(): RibActivityInfoProvider
}