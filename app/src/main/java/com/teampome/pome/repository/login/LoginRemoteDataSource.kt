package com.teampome.pome.repository.login

import com.teampome.pome.model.base.BasePomeResponse
import com.teampome.pome.model.user.UserInfoData
import com.teampome.pome.model.request.PhoneNumberDataBody
import com.teampome.pome.network.LoginService
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val service: LoginService
): LoginDataSource{
    override fun login(body: PhoneNumberDataBody): Flow<ApiResponse<BasePomeResponse<UserInfoData>>> = apiRequestFlow {
        service.login(body)
    }
}