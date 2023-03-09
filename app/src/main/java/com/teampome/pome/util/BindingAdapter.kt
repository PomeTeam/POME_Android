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
import com.teampome.pome.model.RecordData
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.common.Constants
import com.teampome.pome.util.common.Emotion
import com.teampome.pome.util.customview.PomeBigGoalCardView
import com.teampome.pome.util.customview.PomeSmallGoalCardView
import jp.wasabeef.glide.transformations.MaskTransformation

// attrs와 정의하려고 하였지만 둘다 정의해야 에러가 안남...
@BindingAdapter("setRemainDays")
fun bindingSetRemainDays(pomeSmallGoalCardView: PomeSmallGoalCardView, days: String?) {
    pomeSmallGoalCardView.remainDays = days?.let {
        Integer.parseInt(it)
    } ?: 0
}

@BindingAdapter("setUsedAmount")
fun bindingSetUsedAmount(pomeBigGoalCardView: PomeBigGoalCardView, amount: String?) {
    amount?.let {
        pomeBigGoalCardView.useAmount = amount
    }
}

@BindingAdapter("setPomeProgress")
fun bindingSetPomeProgress(pomeBigGoalCardView: PomeBigGoalCardView, progress: Int?) {
    progress?.let {
        pomeBigGoalCardView.progress = it
    } ?: run {
        pomeBigGoalCardView.progress = 0
    }
}

@BindingAdapter("pomeImage44")
fun bindingPomeImage44(imageView: ImageView, src: String?){
    src?.let {
        GlideApp.with(imageView)
            .load(src)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        MaskTransformation(R.drawable.user_profile_empty_150)
                    )
                )
            ).into(imageView)
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.user_profile_empty_150)
            .into(imageView)
    }
}

@BindingAdapter("remindLastEmotionText")
fun bindingRemindLastEmotionText(textView: AppCompatTextView, emotion: Emotion?) {
    emotion?.let {
        when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                textView.text = Constants.HAPPY_EMOTION
            }
            Emotion.WHAT_EMOTION -> {
                textView.text = Constants.WHAT_EMOTION
            }
            Emotion.SAD_EMOTION -> {
                textView.text = Constants.SAD_EMOTION
            }
            Emotion.EMPTY_EMOTION -> {
                textView.text = Constants.EMPTY_EMOTION
            }
        }
    } ?: run {
        textView.text = textView.context.getString(R.string.remind_review_last_emotion_text)
    }
}

@BindingAdapter("remindFirstEmotionText")
fun bindingRemindFirstEmotionText(textView: AppCompatTextView, emotion: Emotion?) {
    emotion?.let {
        when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                textView.text = Constants.HAPPY_EMOTION
            }
            Emotion.WHAT_EMOTION -> {
                textView.text = Constants.WHAT_EMOTION
            }
            Emotion.SAD_EMOTION -> {
                textView.text = Constants.SAD_EMOTION
            }
            Emotion.EMPTY_EMOTION -> {
                textView.text = Constants.EMPTY_EMOTION
            }
        }
    } ?: run {
        textView.text = textView.context.getString(R.string.remind_review_first_emotion_text)
    }
}

@BindingAdapter("remindFirstEmotionDrawable")
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

@BindingAdapter("remindLastEmotionDrawable")
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

@BindingAdapter("recordAlarmsIcon")
fun bindingRecordAlarmsIcon(imageView: ImageView, data: String?) {
    data?.let {
        when(data) {
            "돌아보기" -> {
                Glide.with(imageView)
                    .load(R.drawable.ic_heart_16)
                    .into(imageView)
            }
            "목표종료" -> {
                Glide.with(imageView)
                    .load(R.drawable.ic_check_16)
                    .into(imageView)
            }
            else -> {
                Glide.with(imageView)
                    .load(R.drawable.ic_heart_16)
                    .into(imageView)
            }
        }
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.ic_heart_16)
            .into(imageView)
    }
}

/**
 *  category list state관련 textColor setting
 */
@BindingAdapter("isSelected", "isEnd")
fun bindingCategoryColor(textView: TextView, isSelected: Boolean?, isEnd: Boolean?) {
    if(isSelected == true) {
        textView.setTextColor(textView.context.resources.getColor(R.color.white, null))
    } else {
        if(isEnd == true) {
            textView.setTextColor(textView.context.resources.getColor(R.color.grey_3, null))
        } else {
            textView.setTextColor(textView.context.resources.getColor(R.color.grey_5, null))
        }
    }
}

/**
 *  remind item의 목표 + 시간 표시
 */
@BindingAdapter("goalConnectTime")
fun bindingGoalConnectTime(textView: TextView, recordData: RecordData?) {
    recordData?.let {record ->
        record.createdAt?.let { createdAt ->
            textView.text = textView.context.getString(R.string.connect_dot_text, recordData.oneLineMind, CommonUtil.changeAfterTime(createdAt))
        }
    }
}
