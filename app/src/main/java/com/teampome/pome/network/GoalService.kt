package com.teampome.pome.network

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.AllGoalData
import retrofit2.Response
import retrofit2.http.GET

interface GoalService {

    @GET("api/v1/goals/users")
    suspend fun findAllGoalByUser() : Response<BasePomeResponse<AllGoalData>>
}