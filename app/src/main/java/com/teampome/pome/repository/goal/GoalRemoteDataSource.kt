package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.request.CommentDataBody
import com.teampome.pome.model.request.GoalDataBody
import com.teampome.pome.network.GoalService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRemoteDataSource @Inject constructor(
    private val service: GoalService
) : GoalDataSource {
    override fun findAllGoalByUser(): Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = apiRequestFlow {
        service.findAllGoalByUser()
    }

    override fun getGoalByGoalId(goalId: String): Flow<ApiResponse<BasePomeResponse<GoalData>>> = apiRequestFlow {
        service.getGoalByGoalId(goalId)
    }

    override fun makeGoal(goalDataBody: GoalDataBody): Flow<ApiResponse<BasePomeResponse<GoalData>>> = apiRequestFlow {
        service.makeGoal(goalDataBody)
    }

    override fun deleteGoal(goalId: Int): Flow<ApiResponse<BasePomeResponse<Any>>> = apiRequestFlow {
        service.deleteGoal(goalId)
    }

    override fun getGoalIdByGoalCategoryId(goalCategoryId: Int): Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = apiRequestFlow {
        service.getGoalIdByGoalCategoryId(goalCategoryId)
    }

    override fun finishGoal(
        goalId: Int,
        commentDataBody: CommentDataBody
    ): Flow<ApiResponse<BasePomeResponse<GoalData>>> = apiRequestFlow {
        service.finishGoal(goalId, commentDataBody)
    }

    override fun findEndGoals(): Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = apiRequestFlow {
        service.findEndGoals()
    }
}