package com.teampome.pome.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RecordTestData(
    val recordGoalData: List<RecordGoalItem>?,
    val recordWeekData: RecordWeekData?
)

data class RecordGoalItem(
    val category: String,
    val recordGoalCard: RecordGoalCard,
)

data class RecordGoalCard(
    val isPrivate: Boolean,
    val timeLimit: Int,
    val title: String,
    val useAmount: String,
    val goalAmount: String,
    val progress: Int
)

data class RecordWeekData(
    val remindCount: Int,
    val recordWeekItem: List<RecordWeekItem>?
)

@Parcelize
data class RecordWeekItem(
    val firstThink: String,
    val lastThink: String?,
    val title: String,
    val subTitle: String,
    val date: String
) : Parcelable