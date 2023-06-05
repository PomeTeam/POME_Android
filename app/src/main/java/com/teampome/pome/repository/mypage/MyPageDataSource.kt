package com.teampome.pome.repository.mypage

import com.teampome.pome.model.base.BaseAllData
import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.model.mytab.MyTabMarshmello
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface MyPageDataSource {
    fun getPastGoals() : Flow<ApiResponse<BasePomeResponse<BaseAllData<GoalData>>>>
    fun getMarshmello() : Flow<ApiResponse<BasePomeResponse<List<MyTabMarshmello>>>>
}