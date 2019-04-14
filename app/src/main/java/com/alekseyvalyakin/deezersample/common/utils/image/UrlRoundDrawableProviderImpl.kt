package com.alekseyvalyakin.deezersample.common.utils.image

import com.alekseyvalyakin.deezersample.common.resources.ResourcesProvider
import com.bumptech.glide.request.RequestOptions

class UrlRoundDrawableProviderImpl(
        url: String,
        resourcesProvider: ResourcesProvider
) : UrlDrawableProviderImpl(url, resourcesProvider, RequestOptions.circleCropTransform())