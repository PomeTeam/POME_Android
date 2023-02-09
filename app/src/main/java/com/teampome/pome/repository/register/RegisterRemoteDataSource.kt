package com.teampome.pome.repository.register

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.request.PhoneDataBody
import com.teampome.pome.model.SmsData
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.NicknameDataBody
import com.teampome.pome.model.request.UserInfoDataBody
import com.teampome.pome.network.RegisterService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRemoteDataSource @Inject constructor(
    private val service: RegisterService
) : RegisterDataSource {

    override fun sendSms(phoneNum: String): Flow<ApiResponse<BasePomeResponse<SmsData>>> = apiRequestFlow {
        service.sendSms(PhoneDataBody(phoneNum))
    }

    override fun checkNickname(nickName: String): Flow<ApiResponse<BasePomeResponse<Boolean>>> = apiRequestFlow {
        service.checkNickname(NicknameDataBody(nickName))
    }

    override fun signUp(userInfoDataBody: UserInfoDataBody): Flow<ApiResponse<BasePomeResponse<UserInfoData>>> = apiRequestFlow {
        service.signUp(userInfoDataBody)
    }
}