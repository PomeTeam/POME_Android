package com.teampome.pome.repository.goal

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.request.GoalDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val dataSource: GoalDataSource
) {
    fun findAllGoalByUser() = dataSource.findAllGoalByUser()
    fun getGoalByGoalId(goalId: String) = dataSource.getGoalByGoalId(goalId)
    fun makeGoal(
        endDate: String,
        isPublic: Boolean,
        name: String,
        oneLineMind: String,
        price: Long,
        startDate: String
    ): Flow<ApiResponse<BasePomeResponse<GoalData>>> {
        return dataSource.makeGoal(
            GoalDataBody(
                endDate, isPublic, name, oneLineMind, price, startDate
            )
        )
    }

    fun deleteGoal(goalId: Int) = dataSource.deleteGoal(goalId)
}