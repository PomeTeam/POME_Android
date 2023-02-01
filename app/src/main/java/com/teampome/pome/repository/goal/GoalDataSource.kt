package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface GoalDataSource {
    fun findAllGoalByUser() : Flow<ApiResponse<BasePomeResponse<AllGoalData>>>
}