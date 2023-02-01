package com.teampome.pome.model.goal

data class GoalData(
    val endData: String,
    val goalCategoryResponse: GoalCategoryResponse,
    val id: Int,
    val isEnd: Boolean,
    val isPublic: Boolean,
    val nickname: String,
    val oneLineMind: String,
    val price: Long,
    val startDate: String
)