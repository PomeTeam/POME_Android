package com.teampome.pome.model.goal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalCategory(
    val id: Int,
    val name: String,
    var isSelected: Boolean? = false,
    val goalId: Int
) : Parcelable