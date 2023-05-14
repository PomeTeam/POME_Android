package com.teampome.pome.model

import android.os.Parcelable
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.util.common.GoalState
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordViewType
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
    var viewType: @RawValue RecordViewType? = RecordViewType.Contents,
    var oneWeekCount: Int? = 0,
    var goalDetail: GoalData?,
    var goalState: @RawValue GoalState? = GoalState.Empty
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