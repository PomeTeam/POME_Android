package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.network.GoalService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRemoteDataSource @Inject constructor(
    private val service: GoalService
) : GoalDataSource {
    override fun findAllGoalByUser(): Flow<ApiResponse<BasePomeResponse<AllGoalData>>> = apiRequestFlow {
        service.findAllGoalByUser()
    }

    override fun getGoalByGoalId(goalId: String): Flow<ApiResponse<BasePomeResponse<GoalData>>> = apiRequestFlow {
        service.getGoalByGoalId(goalId)
    }
}