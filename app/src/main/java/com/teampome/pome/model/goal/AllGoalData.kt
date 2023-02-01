package com.teampome.pome.model.goal

import com.teampome.pome.model.base.Pageable
import com.teampome.pome.model.base.Sort

data class AllGoalData(
    val content: List<GoalData>?,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
)