package com.teampome.pome.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.teampome.pome.R
import jp.wasabeef.glide.transformations.MaskTransformation

@BindingAdapter("bind:pomeImage44")
fun bindingPomeImage44(imageView: ImageView, src: String?){
    src?.let {
        Glide.with(imageView)
            .load(src)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        MaskTransformation(R.drawable.mask_pome_44)
                    )
                )
            ).into(imageView)
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.mask_pome_44)
            .into(imageView)
    }
}