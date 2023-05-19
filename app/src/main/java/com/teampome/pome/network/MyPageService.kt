package com.teampome.pome.network

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.mytab.MyTabMarshmello
import retrofit2.Response
import retrofit2.http.GET

//마이 탭 서비스
interface MyPageService {

    // 지난 목표 조회
    @GET("api/v1/goals/users/end")
    suspend fun getUserEndGoals() : Response<BasePomeResponse<BaseAllData<GoalData>>>

    // 마시멜로우 조합
    @GET("api/v1/marshmello/list")
    suspend fun getMarshmello()
        : Response<BasePomeResponse<List<MyTabMarshmello>>>
}