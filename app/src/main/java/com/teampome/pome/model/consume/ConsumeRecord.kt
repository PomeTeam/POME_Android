package com.teampome.pome.model.consume

import android.os.Parcelable
import com.teampome.pome.model.goal.GoalCategoryResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsumeRecord(
    val category: GoalCategoryResponse,
    val date: String,
    val price: Long,
    val record: String
): Parcelable