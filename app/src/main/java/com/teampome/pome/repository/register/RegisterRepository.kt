package com.teampome.pome.repository.register

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserSignUpData
import com.teampome.pome.model.request.UserInfoDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerDataSource: RegisterDataSource
) {
    fun sendSms(phoneNum: String) = registerDataSource.sendSms(phoneNum)
    fun checkNickname(nickName: String) = registerDataSource.checkNickname(nickName)
    fun signUp(imageKey: String, nickname: String, phoneNum: String): Flow<ApiResponse<BasePomeResponse<UserSignUpData>>> {
        return registerDataSource.signUp(
            UserInfoDataBody(
                imageKey,
                nickname,
                phoneNum
            )
        )
    }
}