package com.teampome.pome.util

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.teampome.pome.R
import com.teampome.pome.viewmodel.Emotion
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

@BindingAdapter("bind:remindEmotionText")
fun bindingRemindEmotionText(textView: AppCompatTextView, emotion: Emotion?) {
    emotion?.let {
        when(emotion) {
            Emotion.FIRST_EMOTION -> {
                textView.text = Constants.FIRST_EMOTION
            }
            Emotion.LAST_EMOTION -> {
                textView.text = Constants.LAST_EMOTION
            }
            Emotion.HAPPY_EMOTION -> {
                textView.text = Constants.HAPPY_EMOTION
            }
            Emotion.WHAT_EMOTION -> {
                textView.text = Constants.WHAT_EMOTION
            }
            Emotion.SAD_EMOTION -> {
                textView.text = Constants.SAD_EMOTION
            }
        }
    }
}