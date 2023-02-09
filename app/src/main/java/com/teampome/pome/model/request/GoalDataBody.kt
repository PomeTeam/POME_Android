package com.teampome.pome.model.request

data class GoalDataBody (
    val endDate: String,
    val isPublic: Boolean,
    val name: String,
    val oneLineMind: String,
    val price: Long,
    val startDate: String
)