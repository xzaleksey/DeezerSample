package com.alekseyvalyakin.deezersample.common.view.recyclerview.image

import com.alekseyvalyakin.deezersample.common.utils.rx.subscribeWithErrorLogging
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import org.jetbrains.anko.image

class FlexibleIconWithTextViewHolder(itemView: IconWithTextView, mAdapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(itemView, mAdapter) {
    private var disposable: Disposable = Disposables.disposed()
    private var lastImageId: String = EMPTY_STRING
    private val iconView = itemView.imageView
    private val textView = itemView.textView

    fun bind(avatarWithTwoLineTextModel: FlexibleAvatarWithOneLineTextModel) {
        val imageProvider = avatarWithTwoLineTextModel.imageProvider
        textView.text = avatarWithTwoLineTextModel.primaryText
        if (iconView.image == null || imageProvider.getId() != lastImageId) {
            iconView.setImageBitmap(null)
            disposable.dispose()
            disposable = imageProvider.observeImage()
                .subscribeWithErrorLogging { i ->
                    Glide.with(iconView)
                        .load(i.getBitmap())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(iconView)
                }
            lastImageId = imageProvider.getId()
        }
    }
}
      