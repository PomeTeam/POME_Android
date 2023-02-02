package com.teampome.pome.repository.goal

import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val dataSource: GoalDataSource
) {
    fun findAllGoalByUser() = dataSource.findAllGoalByUser()
    fun getGoalByGoalId(goalId: String) = dataSource.getGoalByGoalId(goalId)
}