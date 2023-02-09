package com.teampome.pome.presentation.remind

import com.teampome.pome.model.goal.GoalCategory

interface OnCategoryItemClickListener {
    fun onCategoryItemClick(item: GoalCategory, position: Int)
}