package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.request.CommentDataBody
import com.teampome.pome.model.request.GoalDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface GoalDataSource {
    fun findAllGoalByUser() : Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>
    fun getGoalByGoalId(goalId: String) : Flow<ApiResponse<BasePomeResponse<GoalData>>>
    fun makeGoal(goalDataBody: GoalDataBody) : Flow<ApiResponse<BasePomeResponse<GoalData>>>
    fun deleteGoal(goalId: Int) : Flow<ApiResponse<BasePomeResponse<Any>>>
    fun getGoalIdByGoalCategoryId(goalCategoryId: Int) : Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>
    fun finishGoal(goalId: Int, commentDataBody: CommentDataBody) : Flow<ApiResponse<BasePomeResponse<GoalData>>>
}