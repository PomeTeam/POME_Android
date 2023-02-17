package com.teampome.pome.network

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.request.CommentDataBody
import com.teampome.pome.model.request.GoalDataBody
import retrofit2.Response
import retrofit2.http.*

interface GoalService {

    /**
     *  모든 목표 조회
     */
    @GET("api/v1/goals/users")
    suspend fun findAllGoalByUser() : Response<BasePomeResponse<BaseAllData<GoalData>>>

    /**
     *  goalId로 하나의 목표 조회
     */
    @GET("api/v1/goals/{goalId}")
    suspend fun getGoalByGoalId(
        @Path("goalId") goalId: String
    ) : Response<BasePomeResponse<GoalData>>

    /**
     *  목표 생성
     */
    @POST("api/v1/goals")
    suspend fun makeGoal(
        @Body goalDataBody: GoalDataBody
    ) : Response<BasePomeResponse<GoalData>>

    /**
     *  목표 삭제
     */
    @DELETE("api/v1/goals/{goalId}")
    suspend fun deleteGoal(
        @Path("goalId") goalId: Int
    ) : Response<BasePomeResponse<Any>>

    /**
     *  목표 카테고리로 목표 조회
     */
    @GET("api/v1/goals/category/{goalCategoryId}")
    suspend fun getGoalIdByGoalCategoryId(
        @Path("goalCategoryId") goalCategoryId: Int
    ) : Response<BasePomeResponse<BaseAllData<GoalData>>>

    /**
     *  목표 종료하기
     */
    @PUT("/api/v1/goals/end/{goalId}")
    suspend fun finishGoal(
        @Path("goalId") goalId: Int,
        @Body commentDataBody: CommentDataBody
    ) : Response<BasePomeResponse<GoalData>>
}