package com.teampome.pome.repository.register

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.SmsData
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RegisterDataSource {
    fun sendSms(phoneNum: String) : Flow<ApiResponse<BasePomeResponse<SmsData>>>

    fun checkNickname(nickName: String) : Flow<ApiResponse<BasePomeResponse<Boolean>>>
}