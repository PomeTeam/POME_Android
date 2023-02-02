package com.teampome.pome.network

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import com.teampome.pome.model.goal.GoalData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GoalService {

    @GET("api/v1/goals/users")
    suspend fun findAllGoalByUser() : Response<BasePomeResponse<AllGoalData>>

    @GET("api/v1/goals/{goalId}")
    suspend fun getGoalByGoalId(
        @Path("goalId") goalId: String
    ) : Response<BasePomeResponse<GoalData>>
}