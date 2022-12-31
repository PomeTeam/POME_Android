package com.teampome.pome.util

import android.widget.ImageView
import android.widget.TextView
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
fun bindingRemindEmotionText(textView: TextView, emotion: Emotion?) {
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

@BindingAdapter("bind:remindFirstEmotionDrawable")
fun bindingRemindFirstEmotionDrawable(imageView: ImageView, emotion: Emotion?) {
    emotion?.let{
        when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_mint_fiter_happy)
                    .into(imageView)
            }
            Emotion.WHAT_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_mint_fiter_what)
                    .into(imageView)
            }
            Emotion.SAD_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_mint_fiter_sad)
                    .into(imageView)
            }
            else -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_mono_16)
                    .into(imageView)
            }
        }
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.emoji_mono_16)
            .into(imageView)
    }
}

@BindingAdapter("bind:remindLastEmotionDrawable")
fun bindingRemindLastEmotionDrawable(imageView: ImageView, emotion: Emotion?) {
    emotion?.let{
        when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_pink_fiter_happy)
                    .into(imageView)
            }
            Emotion.WHAT_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_pink_fiter_what)
                    .into(imageView)
            }
            Emotion.SAD_EMOTION -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_pink_fiter_sad)
                    .into(imageView)
            }
            else -> {
                Glide.with(imageView)
                    .load(R.drawable.emoji_mono_16)
                    .into(imageView)
            }
        }
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.emoji_mono_16)
            .into(imageView)
    }
}