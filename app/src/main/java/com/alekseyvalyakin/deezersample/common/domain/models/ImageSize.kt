package com.alekseyvalyakin.deezersample.common.domain.models

enum class ImageSize(val size: Int) {
    SUPER_SMALL(56),
    SMALL(120),
    MEDIUM(250),
    BIG(500),
    XL(1000);

    companion object {
        fun getClosestSize(size: Int): ImageSize {
            for (value in values()) {
                if (size <= value.size) {
                    return value
                }
            }

            return XL
        }
    }
}