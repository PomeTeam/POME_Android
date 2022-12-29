package com.teampome.pome.model

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
    val goalAmount: String
)

data class RecordWeekData(
    val remindCount: Int,
    val recordWeekItem: List<RecordWeekItem>?
)

data class RecordWeekItem(
    val firstThink: String,
    val lastThink: String?,
    val title: String,
    val subTitle: String,
    val date: String
)