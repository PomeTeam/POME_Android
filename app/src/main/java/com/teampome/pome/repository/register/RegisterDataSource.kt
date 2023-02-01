package com.teampome.pome.repository.register

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.SmsData
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.UserInfoDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RegisterDataSource {
    fun sendSms(phoneNum: String) : Flow<ApiResponse<BasePomeResponse<SmsData>>>
    fun checkNickname(nickName: String) : Flow<ApiResponse<BasePomeResponse<Boolean>>>
    fun signUp(userInfoDataBody: UserInfoDataBody) : Flow<ApiResponse<BasePomeResponse<UserInfoData>>>
}