package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.request.GoalDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface GoalDataSource {
    fun findAllGoalByUser() : Flow<ApiResponse<BasePomeResponse<AllGoalData>>>
    fun getGoalByGoalId(goalId: String) : Flow<ApiResponse<BasePomeResponse<GoalData>>>
    fun makeGoal(goalDataBody: GoalDataBody) : Flow<ApiResponse<BasePomeResponse<GoalData>>>
}