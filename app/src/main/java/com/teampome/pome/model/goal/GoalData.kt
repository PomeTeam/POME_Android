package com.teampome.pome.model.goal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalData(
    val endDate: String,
    val goalCategoryResponse: GoalCategoryResponse,
    val id: Int,
    val isEnd: Boolean,
    val isPublic: Boolean,
    val nickname: String,
    val oneLineMind: String?,
    val price: Long,
    val startDate: String,
    val usePrice: Long,
    val oneLineComment: String?
) : Parcelable