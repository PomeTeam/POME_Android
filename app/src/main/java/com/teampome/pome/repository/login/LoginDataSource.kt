package com.teampome.pome.repository.login

import com.teampome.pome.model.BasePomeResponse
import com.teampome.pome.model.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import com.teampome.pome.util.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    fun login(body: PhoneNumberDataBody): Flow<ApiResponse<BasePomeResponse<UserInfoData>>>
}