package com.teampome.pome.model.consume

import android.os.Parcelable
import com.teampome.pome.model.goal.GoalCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsumeRecord(
    val category: GoalCategory,
    val date: String,
    val price: Long,
    val record: String
): Parcelable