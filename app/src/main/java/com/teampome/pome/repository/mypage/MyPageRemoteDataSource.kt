package com.teampome.pome.repository.mypage

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.friend.FriendData
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.network.MyPageService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRemoteDataSource @Inject constructor(
    private val service: MyPageService
): MyPageDataSource{
    override fun getPastGoals(): Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>> = apiRequestFlow {
        service.getUserEndGoals()
    }

    override fun getMarshmello(): Flow<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>> = apiRequestFlow {
        service.getMarshmello()
    }

}