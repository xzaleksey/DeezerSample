package com.alekseyvalyakin.deezersample.common.view.recyclerview

//Common adapter types not to be intersected
object FlexibleLayoutTypes {
    const val HEADER = 1
    const val ICON_WITH_TEXT = 2
    const val SQUARE_ICON_WITH_TWO_TEXTS = 3
    const val THREE_TEXTS = 4
    const val TWO_TEXTS = 5


    fun getLastItemType() = TWO_TEXTS
}