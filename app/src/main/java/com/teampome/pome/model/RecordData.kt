package com.teampome.pome.model

import android.os.Parcelable
import com.teampome.pome.presentation.record.RecordViewType
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RecordData(
    val createdAt: String?,
    val emotionResponse: EmotionResponse?,
    val id: Int?,
    val nickname: String?,
    val oneLineMind: String?,
    val useComment: String?,
    val useDate: String?,
    val usePrice: Long?,
    val viewType: @RawValue RecordViewType? = RecordViewType.Contents
) : Parcelable

@Parcelize
data class EmotionResponse(
    val firstEmotion: Int?,
    val friendEmotions: List<FriendEmotion?>?,
    val myEmotion: Int?,
    val secondEmotion: Int?
) : Parcelable

@Parcelize
data class FriendEmotion(
    val emotionId: Int?,
    val nickname: String?
) : Parcelable