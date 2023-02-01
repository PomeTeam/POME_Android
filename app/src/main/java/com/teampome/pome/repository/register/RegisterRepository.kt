package com.teampome.pome.repository.register

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.UserInfoDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerDataSource: RegisterDataSource
) {
    fun sendSms(phoneNum: String) = registerDataSource.sendSms(phoneNum)
    fun checkNickname(nickName: String) = registerDataSource.checkNickname(nickName)
    fun signUp(imageKey: String, nickname: String, phoneNum: String): Flow<ApiResponse<BasePomeResponse<UserInfoData>>> {
        return registerDataSource.signUp(
            UserInfoDataBody(
                imageKey,
                nickname,
                phoneNum
            )
        )
    }
}