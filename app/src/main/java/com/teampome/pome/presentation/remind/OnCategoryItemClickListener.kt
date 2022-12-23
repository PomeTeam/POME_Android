package com.teampome.pome.presentation.remind

import com.teampome.pome.model.RemindCategoryData

interface OnCategoryItemClickListener {
    fun onCategoryItemClick(item: RemindCategoryData, position: Int)
}