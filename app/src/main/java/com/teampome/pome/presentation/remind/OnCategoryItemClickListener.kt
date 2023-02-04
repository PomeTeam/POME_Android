package com.teampome.pome.presentation.remind

import com.teampome.pome.model.goal.GoalCategoryResponse

interface OnCategoryItemClickListener {
    fun onCategoryItemClick(item: GoalCategoryResponse, position: Int)
}